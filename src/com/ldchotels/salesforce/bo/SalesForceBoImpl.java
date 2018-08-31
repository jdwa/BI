package com.ldchotels.salesforce.bo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.ldchotels.util.SalesforceProperty;
import com.sforce.async.AsyncApiException;
import com.sforce.async.BatchInfo;
import com.sforce.async.BatchStateEnum;
import com.sforce.async.BulkConnection;
import com.sforce.async.CSVReader;
import com.sforce.async.ConcurrencyMode;
import com.sforce.async.ContentType;
import com.sforce.async.JobInfo;
import com.sforce.async.JobStateEnum;
import com.sforce.async.OperationEnum;
import com.sforce.async.QueryResultList;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

public class SalesForceBoImpl implements SalesForceBo {
	
	private static Logger logger = Logger.getLogger(SalesForceBoImpl.class.getName());
	
	private SalesforceProperty sfProperty;
	
	public SalesForceBoImpl (SalesforceProperty sfProperty) {
		this.sfProperty = sfProperty;
	}
	/**
	 * Creates a Bulk API job and uploads batches for a CSV file.
	 */
	public void upsertToSF(String sobjectType, String authorization, String userName, String password,
			String fileName, String externalIdFieldName, ConcurrencyMode connectionMode) throws AsyncApiException,
			ConnectionException, IOException {
		BulkConnection connection = getBulkConnection(authorization, userName, password);
		JobInfo job = createJob(sobjectType, OperationEnum.upsert, connection, externalIdFieldName, connectionMode);
		List<BatchInfo> batchInfoList = createBatchesFromCSVFile(connection, job, fileName);
		closeJob(connection, job.getId());
		awaitCompletion(connection, job, batchInfoList);
		checkResults(connection, job, batchInfoList);
	}
	
	/**
	 * Creates a Bulk API job and delete batches for a CSV file.
	 */
	public void deleteInSF(String sobjectType, String authorization, String userName, String password,
			String fileName, String externalIdFieldName, ConcurrencyMode connectionMode) throws AsyncApiException,
			ConnectionException, IOException {
		BulkConnection connection = getBulkConnection(authorization, userName, password);
		JobInfo job = createJob(sobjectType, OperationEnum.delete, connection, externalIdFieldName, connectionMode);
		List<BatchInfo> batchInfoList = createBatchesFromCSVFile(connection, job, fileName);
		closeJob(connection, job.getId());
		awaitCompletion(connection, job, batchInfoList);
		checkResults(connection, job, batchInfoList);
	}

	/**
	 * Gets the results of the operation and checks for errors.
	 */
	private void checkResults(BulkConnection connection, JobInfo job,
			List<BatchInfo> batchInfoList) throws AsyncApiException,
			IOException {
		// batchInfoList was populated when batches were created and submitted
		for (BatchInfo b : batchInfoList) {
			try {
				CSVReader rdr = new CSVReader(connection.getBatchResultStream(job.getId(), b.getId()));
				List<String> resultHeader = rdr.nextRecord();
				int resultCols = resultHeader.size();
	
				List<String> row;
				while ((row = rdr.nextRecord()) != null) {
					Map<String, String> resultInfo = new HashMap<String, String>();
					for (int i = 0; i < resultCols; i++) {
						resultInfo.put(resultHeader.get(i), row.get(i));
						logger.debug("====== Recoder [" + i + "] Header:[" + resultHeader.get(i) + "] Row :[" + row.get(i) + "]");					
					}
					boolean success = Boolean.valueOf(resultInfo.get("Success"));
					boolean created = Boolean.valueOf(resultInfo.get("Created"));
					String id = resultInfo.get("Id");
					String error = resultInfo.get("Error");
					if (success && created) {
						logger.debug("Created row with id " + id);
					} else if (!success) {
						logger.info("Failed with error: " + error);
					}
				}
			} catch(AsyncApiException ase){
				logger.info("Failed with AsyncApiException : " + ase);
			}
		}
	}

	private void closeJob(BulkConnection connection, String jobId)
			throws AsyncApiException {
		JobInfo job = new JobInfo();
		job.setId(jobId);
		job.setState(JobStateEnum.Closed);
		connection.updateJob(job);
	}

	/**
	 * Wait for a job to complete by polling the Bulk API.
	 * 
	 * @param connection
	 *            BulkConnection used to check results.
	 * @param job
	 *            The job awaiting completion.
	 * @param batchInfoList
	 *            List of batches for this job.
	 * @throws AsyncApiException
	 */
	private void awaitCompletion(BulkConnection connection, JobInfo job,
			List<BatchInfo> batchInfoList) throws AsyncApiException {
		long sleepTime = 0L;
		Set<String> incomplete = new HashSet<String>();
		for (BatchInfo bi : batchInfoList) {
			incomplete.add(bi.getId());
		}
		while (!incomplete.isEmpty()) {
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
			}
			logger.info("Awaiting results..." + incomplete.size());
			sleepTime = this.sfProperty.getSleepTime();
			BatchInfo[] statusList = connection.getBatchInfoList(job.getId()).getBatchInfo();
			for (BatchInfo b : statusList) {
				if (b.getState() == BatchStateEnum.Completed || b.getState() == BatchStateEnum.Failed) {
					if (incomplete.remove(b.getId())) {
						logger.debug("BATCH STATUS:\n" + b);
					}
				}
			}
		}
	}

	/**
	 * Create a new job using the Bulk API.
	 * 
	 * @param sobjectType
	 *            The object type being loaded, such as "Account"
	 * @param connection
	 *            BulkConnection used to create the new job.
	 * @return The JobInfo for the new job.
	 * @throws AsyncApiException
	 */
	private JobInfo createJob(String sobjectType, OperationEnum operationType, BulkConnection connection, String externalIdFieldName, ConcurrencyMode connectionMode)
			throws AsyncApiException {
		JobInfo job = new JobInfo();
		job.setObject(sobjectType);
		job.setOperation(operationType);
		if (externalIdFieldName != null) {
			job.setExternalIdFieldName(externalIdFieldName);
		}
		job.setConcurrencyMode(connectionMode);
		job.setContentType(ContentType.CSV);	
		job = connection.createJob(job);
		logger.info(job);
		return job;
	}

	/**
	 * Create the BulkConnection used to call Bulk API operations.
	 */
	private BulkConnection getBulkConnection(String authorization, String userName, String password)
			throws ConnectionException, AsyncApiException {
		ConnectorConfig partnerConfig = new ConnectorConfig();
		partnerConfig.setUsername(userName);
		partnerConfig.setPassword(password);
		partnerConfig.setAuthEndpoint(authorization);
		//partnerConfig.setAuthEndpoint("https://login.salesforce.com/services/Soap/u/41.0");
		// Creating the connection automatically handles login and stores
		// the session in partnerConfig
		new PartnerConnection(partnerConfig);
		// When PartnerConnection is instantiated, a login is implicitly
		// executed and, if successful,
		// a valid session is stored in the ConnectorConfig instance.
		// Use this key to initialize a BulkConnection:
		ConnectorConfig config = new ConnectorConfig();
		config.setSessionId(partnerConfig.getSessionId());
		// The endpoint for the Bulk API service is the same as for the normal
		// SOAP uri until the /Soap/ part. From here it's '/async/versionNumber'
		String soapEndpoint = partnerConfig.getServiceEndpoint();
		String apiVersion = "42.0";
		String restEndpoint = soapEndpoint.substring(0,
				soapEndpoint.indexOf("Soap/"))
				+ "async/" + apiVersion;
		config.setRestEndpoint(restEndpoint);
		// This should only be false when doing debugging.
		config.setCompression(true);
		// Set this to true to see HTTP requests and responses on stdout
		config.setTraceMessage(false);
		BulkConnection connection = new BulkConnection(config);
		return connection;
	}

	/**
	 * Create and upload batches using a CSV file. The file into the appropriate
	 * size batch files.
	 * 
	 * @param connection
	 *            Connection to use for creating batches
	 * @param jobInfo
	 *            Job associated with new batches
	 * @param csvFileName
	 *            The source file for batch data
	 */
	private List<BatchInfo> createBatchesFromCSVFile(BulkConnection connection,
			JobInfo jobInfo, String csvFileName) throws IOException, AsyncApiException {
		List<BatchInfo> batchInfos = new ArrayList<BatchInfo>();
		BufferedReader rdr = new BufferedReader(new InputStreamReader(new FileInputStream(csvFileName)));
		// read the CSV header row
		byte[] headerBytes = (rdr.readLine() + "\n").getBytes("UTF-8");
		int headerBytesLength = headerBytes.length;	
		File tmpFile = File.createTempFile("BulkAPITemp", ".csv");

		// Split the CSV file into multiple batches
		try {
			FileOutputStream tmpOut = new FileOutputStream(tmpFile);
			int maxBytesPerBatch = this.sfProperty.getMaxBytesPerBatch(); // 10 million bytes per batch
			int maxRowsPerBatch = this.sfProperty.getMaxRowsPerBatch(); // 10 thousand rows per batch
			int currentBytes = 0;
			int currentLines = 0;
			String nextLine;
			while ((nextLine = rdr.readLine()) != null) {
				byte[] bytes = (nextLine + "\n").getBytes("UTF-8");
				// Create a new batch when our batch size limit is reached
				if (currentBytes + bytes.length > maxBytesPerBatch || currentLines > maxRowsPerBatch) {
					createBatch(tmpOut, tmpFile, batchInfos, connection, jobInfo);
					currentBytes = 0;
					currentLines = 0;
				}
				if (currentBytes == 0) {
					tmpOut = new FileOutputStream(tmpFile);
					tmpOut.write(headerBytes);
					currentBytes = headerBytesLength;
					currentLines = 1;
				}
				tmpOut.write(bytes);
				currentBytes += bytes.length;
				currentLines++;
			}
			// Finished processing all rows
			// Create a final batch for any remaining data
			if (currentLines > 1) {
				createBatch(tmpOut, tmpFile, batchInfos, connection, jobInfo);
			}
		} finally {
			rdr.close();
			tmpFile.delete();
		}
		return batchInfos;
	}

	/**
	 * Create a batch by uploading the contents of the file. This closes the
	 * output stream.
	 * 
	 * @param tmpOut
	 *            The output stream used to write the CSV data for a single
	 *            batch.
	 * @param tmpFile
	 *            The file associated with the above stream.
	 * @param batchInfos
	 *            The batch info for the newly created batch is added to this
	 *            list.
	 * @param connection
	 *            The BulkConnection used to create the new batch.
	 * @param jobInfo
	 *            The JobInfo associated with the new batch.
	 */
	private void createBatch(FileOutputStream tmpOut, File tmpFile,
			List<BatchInfo> batchInfos, BulkConnection connection,
			JobInfo jobInfo) throws IOException, AsyncApiException {
		tmpOut.flush();
		tmpOut.close();
		FileInputStream tmpInputStream = new FileInputStream(tmpFile);
		try {
			BatchInfo batchInfo = connection.createBatchFromStream(jobInfo, tmpInputStream);
			logger.debug(batchInfo);
			batchInfos.add(batchInfo);
		} finally {
			tmpInputStream.close();
		}
	}
	
	/* For Bulk Query */
	public void doBulkQueryAccount(String authorization, String userName,
			String password, String filePath) throws AsyncApiException, InterruptedException,
			ConnectionException, FileNotFoundException, IOException {

		BulkConnection bulkConnection = getBulkConnection(authorization, userName, password);

		JobInfo job = new JobInfo();
		job.setObject("Account");
		job.setOperation(OperationEnum.query);
		job.setConcurrencyMode(ConcurrencyMode.Parallel);
		job.setContentType(ContentType.CSV);
		job = bulkConnection.createJob(job);
		assert job.getId() != null;

		job = bulkConnection.getJobStatus(job.getId());

		String query = "select Guest_Profile_No__c, Id from Account";

		// -- long start = System.currentTimeMillis();

		BatchInfo info = null;
		ByteArrayInputStream bout = new ByteArrayInputStream(query.getBytes());
		info = bulkConnection.createBatchFromStream(job, bout);

		String[] queryResults = null;

		for (int i = 0; i < 10000; i++) {
			Thread.sleep(this.sfProperty.getSleepTime());
			info = bulkConnection.getBatchInfo(job.getId(), info.getId());

			if (info.getState() == BatchStateEnum.Completed) {
				QueryResultList list = bulkConnection.getQueryResultList(
						job.getId(), info.getId());
				queryResults = list.getResult();
				logger.info("Completed : " + info);
				break;
			} else if (info.getState() == BatchStateEnum.Failed) {
				logger.info("Failed : " + info);
				break;
			} else {
				logger.info("Waiting : " + info);
			}
		}

		if (queryResults != null) {
			for (String resultId : queryResults) {
				InputStream inputStream = bulkConnection.getQueryResultStream(
						job.getId(), info.getId(), resultId);
				File outfile = new File(filePath);
				FileOutputStream outputStream = new FileOutputStream(outfile);
				BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
				BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

				int i = 0;
				byte[] buffer = new byte[512];
				try {
					while (true) {
						if (bufferedInputStream.available() < 512) {
							while (i != -1) {
								i = bufferedInputStream.read();
								bufferedOutputStream.write(i);
							}
							break;
						} else {
							// more than 512
							bufferedInputStream.read(buffer);
							bufferedOutputStream.write(buffer);
						}
					}
				} finally {
					bufferedOutputStream.flush();
					bufferedInputStream.close();
					bufferedOutputStream.close();
				}
			}
		}
		
		closeJob(bulkConnection, job.getId());
	}

	public void doBulkQueryAccountForDelete(String authorization, String userName,
			String password, String filePath, String delChgBegin,
			String delChgEnd) throws AsyncApiException, InterruptedException,
			ConnectionException, FileNotFoundException, IOException {
		
		BulkConnection bulkConnection = getBulkConnection(authorization, userName, password);

		JobInfo job = new JobInfo();
		job.setObject("Account");
		job.setOperation(OperationEnum.query);
		job.setConcurrencyMode(ConcurrencyMode.Parallel);
		job.setContentType(ContentType.CSV);
		job = bulkConnection.createJob(job);
		assert job.getId() != null;

		job = bulkConnection.getJobStatus(job.getId());

		// String query = "select Id from Reservation__c"; // The 'delete' batch must contain only ids.
		String query = "select Id from Account where LastModifiedDate >= " + delChgBegin + "T00:00:00Z and LastModifiedDate <= " + delChgEnd + "T23:59:59Z";
		// String query = "select Id, LastModifiedDate from Account";
		
		// -- long start = System.currentTimeMillis();

		BatchInfo info = null;
		ByteArrayInputStream bout = new ByteArrayInputStream(query.getBytes());
		info = bulkConnection.createBatchFromStream(job, bout);

		String[] queryResults = null;

		while (true) {
			Thread.sleep(this.sfProperty.getSleepTime());
			info = bulkConnection.getBatchInfo(job.getId(), info.getId());

			if (info.getState() == BatchStateEnum.Completed) {
				QueryResultList list = bulkConnection.getQueryResultList(
						job.getId(), info.getId());
				queryResults = list.getResult();
				logger.info("Completed : " + info);
				break;
			} else if (info.getState() == BatchStateEnum.Failed) {
				logger.info("Failed : " + info);
				break;
			} else {
				logger.info("Waiting : " + info);
			}
		}

		if (queryResults != null) {
			for (String resultId : queryResults) {
				InputStream inputStream = bulkConnection.getQueryResultStream(
						job.getId(), info.getId(), resultId);
				File outfile = new File(filePath);
				FileOutputStream outputStream = new FileOutputStream(outfile);
				BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
				BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

				int i = 0;
				byte[] buffer = new byte[512];
				try {
					while (true) {
						if (bufferedInputStream.available() < 512) {
							while (i != -1) {
								i = bufferedInputStream.read();
								bufferedOutputStream.write(i);
							}
							break;
						} else {
							// more than 512
							bufferedInputStream.read(buffer);
							bufferedOutputStream.write(buffer);
						}
					}
				} finally {
					bufferedOutputStream.flush();
					bufferedInputStream.close();
					bufferedOutputStream.close();
				}
			}
		}
		
		closeJob(bulkConnection, job.getId());
	}

	public void doBulkQueryReservationForDelete(String authorization, String userName,
			String password, String filePath, String delArrBegin,
			String delArrEnd) throws AsyncApiException, InterruptedException,
			ConnectionException, FileNotFoundException, IOException {
		
		BulkConnection bulkConnection = getBulkConnection(authorization, userName, password);

		JobInfo job = new JobInfo();
		job.setObject("Reservation__c");
		job.setOperation(OperationEnum.query);
		job.setConcurrencyMode(ConcurrencyMode.Parallel);
		job.setContentType(ContentType.CSV);
		job = bulkConnection.createJob(job);
		assert job.getId() != null;

		job = bulkConnection.getJobStatus(job.getId());

		// String query = "select Id from Reservation__c"; // The 'delete' batch must contain only ids.
		String query = "select Id from Reservation__c where Arrival_date_for_this_room__c >= " + delArrBegin + " and Arrival_date_for_this_room__c <= " + delArrEnd + "";
		
		// -- long start = System.currentTimeMillis();

		BatchInfo info = null;
		ByteArrayInputStream bout = new ByteArrayInputStream(query.getBytes());
		info = bulkConnection.createBatchFromStream(job, bout);

		String[] queryResults = null;

		while (true) {
			Thread.sleep(this.sfProperty.getSleepTime());
			info = bulkConnection.getBatchInfo(job.getId(), info.getId());

			if (info.getState() == BatchStateEnum.Completed) {
				QueryResultList list = bulkConnection.getQueryResultList(
						job.getId(), info.getId());
				queryResults = list.getResult();
				logger.info("Completed : " + info);
				break;
			} else if (info.getState() == BatchStateEnum.Failed) {
				logger.info("Failed : " + info);
				break;
			} else {
				logger.info("Waiting : " + info);
			}
		}

		if (queryResults != null) {
			for (String resultId : queryResults) {
				InputStream inputStream = bulkConnection.getQueryResultStream(
						job.getId(), info.getId(), resultId);
				File outfile = new File(filePath);
				FileOutputStream outputStream = new FileOutputStream(outfile);
				BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
				BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

				int i = 0;
				byte[] buffer = new byte[512];
				try {
					while (true) {
						if (bufferedInputStream.available() < 512) {
							while (i != -1) {
								i = bufferedInputStream.read();
								bufferedOutputStream.write(i);
							}
							break;
						} else {
							// more than 512
							bufferedInputStream.read(buffer);
							bufferedOutputStream.write(buffer);
						}
					}
				} finally {
					bufferedOutputStream.flush();
					bufferedInputStream.close();
					bufferedOutputStream.close();
				}
			}
		}
		
		closeJob(bulkConnection, job.getId());
	}

	public void doBulkQueryTransactionForDelete(String authorization, String userName,
			String password, String filePath, String delDepBegin,
			String delDepEnd) throws AsyncApiException, InterruptedException,
			ConnectionException, FileNotFoundException, IOException {
		
		BulkConnection bulkConnection = getBulkConnection(authorization, userName, password);

		JobInfo job = new JobInfo();
		job.setObject("Transaction__c");
		job.setOperation(OperationEnum.query);
		job.setConcurrencyMode(ConcurrencyMode.Parallel);
		job.setContentType(ContentType.CSV);
		job = bulkConnection.createJob(job);
		assert job.getId() != null;

		job = bulkConnection.getJobStatus(job.getId());

		// String query = "select Id from Transaction__c"; // The 'delete' batch must contain only ids.
		String query = "select Id from Transaction__c where Departure_date_for_this_room__c >= " + delDepBegin + " and Departure_date_for_this_room__c <= " + delDepEnd + "";
		
		// -- long start = System.currentTimeMillis();

		BatchInfo info = null;
		ByteArrayInputStream bout = new ByteArrayInputStream(query.getBytes());
		info = bulkConnection.createBatchFromStream(job, bout);

		String[] queryResults = null;

		while (true) {
			Thread.sleep(this.sfProperty.getSleepTime());
			info = bulkConnection.getBatchInfo(job.getId(), info.getId());

			if (info.getState() == BatchStateEnum.Completed) {
				QueryResultList list = bulkConnection.getQueryResultList(
						job.getId(), info.getId());
				queryResults = list.getResult();
				logger.info("Completed : " + info);
				break;
			} else if (info.getState() == BatchStateEnum.Failed) {
				logger.info("Failed : " + info);
				break;
			} else {
				logger.info("Waiting : " + info);
			}
		}

		if (queryResults != null) {
			for (String resultId : queryResults) {
				InputStream inputStream = bulkConnection.getQueryResultStream(
						job.getId(), info.getId(), resultId);
				File outfile = new File(filePath);
				FileOutputStream outputStream = new FileOutputStream(outfile);
				BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
				BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

				int i = 0;
				byte[] buffer = new byte[512];
				try {
					while (true) {
						if (bufferedInputStream.available() < 512) {
							while (i != -1) {
								i = bufferedInputStream.read();
								bufferedOutputStream.write(i);
							}
							break;
						} else {
							// more than 512
							bufferedInputStream.read(buffer);
							bufferedOutputStream.write(buffer);
						}
					}
				} finally {
					bufferedOutputStream.flush();
					bufferedInputStream.close();
					bufferedOutputStream.close();
				}
			}
		}
		
		closeJob(bulkConnection, job.getId());
	}
}

