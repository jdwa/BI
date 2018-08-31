package com.ldchotels.salesforce.action;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ldchotels.salesforce.bo.SalesForceBo;
import com.ldchotels.salesforce.bo.SalesForceBoImpl;
import com.ldchotels.util.SalesforceProperty;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.sforce.async.*;
import com.sforce.ws.ConnectionException;

public class TransactionDeleteAction extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(TransactionDeleteAction.class.getName());

	private Map<String, Object> session;
	private SalesforceProperty sfProperty;
	private Date delDepBegin;
	private Date delDepEnd;
	private boolean fileDelete = true;
	private boolean serial = true;

	public TransactionDeleteAction() {
		super();
	}

	/* ActionSupport */
	@Override
	public String execute() throws AsyncApiException, ConnectionException, IOException, Exception {
		String returnValue = ERROR;

		if (this.session != null) {
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");		
			String fileName = sdf.format(now) + "_" + now.getTime() + ".csv";
				
			SalesForceBo sf = new SalesForceBoImpl(sfProperty);
			String authorization = sfProperty.getAuthorization();
			String userName = sfProperty.getUserName();
			String password = sfProperty.getPassword();	
			String fileDir = sfProperty.getFileDir();
			String filePath = fileDir + "Log_Del_Tran_" + fileName;

			// For logging and waiting message
			logger.info("Steps 1/3, Query Transactions from Salesforce and save to local file ...");
			this.addActionMessage("Steps 1/3, Query Transactions from Salesforce and save to local file ...");
			
			// Query Transactions from Salesforce.
			String delDepBegin = "1900-01-01";
			if (delDepBegin != null) {
				delDepBegin = sdf.format(this.getDelDepBegin());
			}
			String delDepEnd = "1900-01-01";
			if (delDepEnd != null) {
				delDepEnd = sdf.format(this.getDelDepEnd());
			}
			
			sf.doBulkQueryTransactionForDelete(authorization, userName, password, filePath, delDepBegin, delDepEnd);
			
			// For logging and waiting message
			logger.info("Steps 2/3, Delete Transactions in Salesforce accroding to local file ..." );
			logger.info("Criteria : Departure date between [" + delDepBegin + "T00:00:00Z] and [" + delDepEnd + "T23:59:59Z]" );
			this.addActionMessage("Steps 2/3, Delete Transactions in Salesforce accroding to local file ...");
			this.addActionMessage("Criteria : Departure date between [" + delDepBegin + "T00:00:00Z] and [" + delDepEnd + "T23:59:59Z]");

			// Delete Transactions in Salesforce.
			ConcurrencyMode mode = (this.isSerial() ? ConcurrencyMode.Serial : ConcurrencyMode.Parallel);
			sf.deleteInSF("Transaction__c", authorization, userName, password, filePath, "Transaction_Number__c", mode);
			this.session.put(this.sfProperty.getTransactionResultFile(), filePath);
			
			// For logging and waiting message
			long timeStamp = System.currentTimeMillis();
			logger.info("Steps 3/3, Total completed. Time consumed (ms) : " + (timeStamp - now.getTime()));
			this.addActionMessage("Steps 3/3, Total completed. Time consumed (ms) : " + (timeStamp - now.getTime()));

			returnValue = SUCCESS;
		}

		return returnValue;
	}

    protected void finalize(){
    	if ((this.session != null) && (this.isFileDelete())) {
    		File outputFile = new File(this.session.get(this.sfProperty.getAccountResultFile()).toString());
    		if (outputFile.exists() && outputFile.delete()){
    			logger.info("File deleted : " + outputFile.getAbsolutePath());
    		};
    		
    		outputFile = new File(this.session.get(this.sfProperty.getReservationResultFile()).toString());
    		if (outputFile.exists() && outputFile.delete()){
    			logger.info("File deleted : " + outputFile.getAbsolutePath());
    		};
    		
    		outputFile = new File(this.session.get(this.sfProperty.getReservationCOResultFile()).toString());
    		if (outputFile.exists() && outputFile.delete()){
    			logger.info("File deleted : " + outputFile.getAbsolutePath());
    		};
    		
    		outputFile = new File(this.session.get(this.sfProperty.getTransactionResultFile()).toString());
    		if (outputFile.exists() && outputFile.delete()){
    			logger.info("File deleted : " + outputFile.getAbsolutePath());
    		};
        }
    }
    
	/* ActionSupport */
	@Override
	public String input() throws Exception {
		return super.input();
	}

	/* ActionSupport */
	@Override
	public void validate() {
		super.validate();
	}

	/* Preparable */
	@Override
	public void prepare() throws Exception {
		if (this.sfProperty == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.sfProperty = (SalesforceProperty) cxt.getBean("sfProperty");
		}
	}

	/* SessionAware */
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public Map<String, Object> getSession() {
		return this.session;
	}

	public Date getDelDepBegin() {
		return delDepBegin;
	}

	public void setDelDepBegin(Date delDepBegin) {
		this.delDepBegin = delDepBegin;
	}

	public Date getDelDepEnd() {
		return delDepEnd;
	}

	public void setDelDepEnd(Date delDepEnd) {
		this.delDepEnd = delDepEnd;
	}

	public boolean isFileDelete() {
		return fileDelete;
	}

	public void setFileDelete(boolean fileDelete) {
		this.fileDelete = fileDelete;
	}

	public boolean isSerial() {
		return serial;
	}

	public void setSerial(boolean serial) {
		this.serial = serial;
	}
}