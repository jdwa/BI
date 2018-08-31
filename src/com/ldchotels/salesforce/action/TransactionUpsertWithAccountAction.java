package com.ldchotels.salesforce.action;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ldchotels.protel.bo.KundenBo;
import com.ldchotels.protel.bo.ProtelBo;
import com.ldchotels.protel.bo.ProtelBoImpl;
import com.ldchotels.protel.bo.ReservationBo;
import com.ldchotels.protel.bo.ReservationCOBo;
import com.ldchotels.protel.bo.TransactionBo;
import com.ldchotels.protel.model.Kunden;
import com.ldchotels.protel.model.Reservation;
import com.ldchotels.protel.model.ReservationCO;
import com.ldchotels.protel.model.Transaction;
import com.ldchotels.salesforce.bo.SalesForceBo;
import com.ldchotels.salesforce.bo.SalesForceBoImpl;
import com.ldchotels.util.SalesforceProperty;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.sforce.async.*;
import com.sforce.ws.ConnectionException;

public class TransactionUpsertWithAccountAction extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(TransactionUpsertWithAccountAction.class.getName());

	private Map<String, Object> session;
	private SalesforceProperty sfProperty;
	private TransactionBo transactionBo;
	private List<Transaction> transactionList; // For List action
	private Reservation reservation; // For Add, Update action
	private ReservationBo reservationBo;
	private List<Reservation> reservationList; // For List action
	private ReservationCO reservationCO; // For Add, Update action
	private ReservationCOBo reservationCOBo;
	private List<ReservationCO> reservationCOList; // For List action	
	private Kunden kunden; // For Add, Update action
	private KundenBo kundenBo;
	private List<Kunden> kundenList; // For List action
	private Date depBegin;
	private Date depEnd;
	private boolean reservationCOUpsert = true;
	private boolean fileDelete = true;
	private boolean serial = true;

	public TransactionUpsertWithAccountAction() {
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
			
			// Get Protel transactions list.
			String depBegin = "1900-01-01";
			if (depBegin != null) {
				depBegin = sdf.format(this.getDepBegin());
			}
			String depEnd = "1900-01-01";
			if (depEnd != null) {
				depEnd = sdf.format(this.getDepEnd());
			}

			logger.info("Criteria : Transactions departure between [" + depBegin + "T00:00:00Z] and [" + depEnd + "T23:59:59Z]");
			this.addActionMessage("Criteria : Transactions departure between [" + depBegin + "T00:00:00Z] and [" + depEnd + "T23:59:59Z]");

			logger.info("Steps 1/6, Getting Transactions from Protel ...");
			this.addActionMessage("Steps 1/6, Getting Transactions from Protel ...");
			
			ProtelBo ptl = new ProtelBoImpl(this.sfProperty, this.transactionBo);
			transactionList = ptl.getTransactionsFromProtel(depBegin, depEnd);

			// For logging and waiting message
			long timeStamp = System.currentTimeMillis();
			logger.info("Completed. Total Transactions : " + transactionList.size() + ", Time consumed (ms): " + (timeStamp - now.getTime()));
			this.addActionMessage("Completed. Total Transactions : " + transactionList.size() + ", Time consumed (ms): " + (timeStamp - now.getTime()));

			logger.info("Steps 2/6, Checking Kundens, Reservations, ReservationCOs ...");
			this.addActionMessage("Steps 2/6, Checking Kundens, Reservations, ReservationCOs ...");
			
			logger.info("Checking Kundens ...");
			this.addActionMessage("Checking Kundens ...");
			
			HashMap<Long, Kunden> kundenHashMap = new HashMap<Long, Kunden>((transactionList.size()/2));	
			kundenList = new ArrayList<Kunden>();
			if (transactionList != null && transactionList.size() > 0) {		
				for (int i = 0; i < transactionList.size(); i++ ) {
					if (kundenHashMap.containsKey(transactionList.get(i).getAccount__c())) {
						continue;
					} else {
						kunden = kundenBo.findByKdnr(transactionList.get(i).getAccount__c());
						kundenHashMap.put(transactionList.get(i).getAccount__c(), kunden);
						kundenList.add(kunden);
					}
				}				
			}
			timeStamp = System.currentTimeMillis();
			logger.info("Checking Kundens completed. Time consumed (ms) : " + (timeStamp - now.getTime()));
			this.addActionMessage("Checking Kundens completed. Time consumed (ms) : " + (timeStamp - now.getTime()));
				
			logger.info("Checking Reservations ...");
			this.addActionMessage("Checking Reservations ...");

			logger.info("Optional : Check ReservationCOs = [" + this.isReservationCOUpsert() + "]");
			this.addActionMessage("Optional : Check ReservationCOs = [" + this.isReservationCOUpsert() + "]");

			HashMap<Long, Reservation> reservationHashMap = new HashMap<Long, Reservation>((transactionList.size()/2));	
			HashMap<Long, ReservationCO> reservationCOHashMap = new HashMap<Long, ReservationCO>((transactionList.size()/2));	
			reservationList = new ArrayList<Reservation>();
			reservationCOList = new ArrayList<ReservationCO>();
			if (transactionList != null && transactionList.size() > 0) {
				for (int i = 0; i < transactionList.size(); i++ ) {
					if (reservationHashMap.containsKey(transactionList.get(i).getReservation_no__c()) || 
							reservationCOHashMap.containsKey(transactionList.get(i).getReservation_no__c())) {
						continue;
					} else {							
						reservation = reservationBo.findByBuchnr(transactionList.get(i).getReservation_no__c());
						if (reservation != null) {
							reservationHashMap.put(transactionList.get(i).getReservation_no__c(), reservation);
							reservationList.add(reservation);
						} else if (this.isReservationCOUpsert()) {
							reservationCO = reservationCOBo.findByBuchnr(transactionList.get(i).getReservation_no__c());
							reservationCOHashMap.put(transactionList.get(i).getReservation_no__c(), reservationCO);
							reservationCOList.add(reservationCO);
						} 
					}
				}
			}
			
			timeStamp = System.currentTimeMillis();
			logger.info("Checking Reservations/ReservationCOs completed. Time consumed (ms) : " + (timeStamp - now.getTime()));
			this.addActionMessage("Checking Reservations/ReservationCOs completed. Time consumed (ms) : " + (timeStamp - now.getTime()));

			// For logging and waiting message
			logger.info("Completed. Total kundens : " + kundenList.size());
			this.addActionMessage("Completed. Total kundens : " + kundenList.size());
			logger.info("Completed. Total Reservations : " + reservationList.size());
			this.addActionMessage("Completed. Total Reservations : " + reservationList.size());
			if (this.isReservationCOUpsert()) {
				logger.info("Completed. Total ReservationCOs : " + reservationCOList.size());
				this.addActionMessage("Completed. Total ReservationCOs : " + reservationCOList.size());
			}

			SalesForceBo sf = new SalesForceBoImpl(sfProperty);
			String authorization = sfProperty.getAuthorization();
			String userName = sfProperty.getUserName();
			String password = sfProperty.getPassword();	
			String fileDir = sfProperty.getFileDir();
			
			// For logging and waiting message
			logger.info("Steps 3/6, Saving Accounts to file and upsert to Salesforce ...");
			this.addActionMessage("Steps 3/6, Saving Accounts to file and upsert to Salesforce ...");
			
			// Save kundens and upsert accounts records.
			String filePath = ptl.saveKundensToFile(fileDir + "Log_Pro_" + fileName, kundenList);
			ConcurrencyMode mode = (this.isSerial() ? ConcurrencyMode.Serial : ConcurrencyMode.Parallel);
			sf.upsertToSF("Account", authorization, userName, password, filePath, "Guest_Profile_No__c", mode);
			this.session.put(this.sfProperty.getAccountResultFile(), filePath);

			// For logging and waiting message
			logger.info("Steps 4/6, Saving Reservations to file and upsert to Salesforce ...");
			this.addActionMessage("Steps 4/6, Saving Reservations to file and upsert to Salesforce ...");

			// Upsert Reservation records related to the Accounts.
			filePath = ptl.saveReservationsToFile(fileDir + "Log_Rev_" + fileName, reservationList);
			sf.upsertToSF("Reservation__c", authorization, userName, password, filePath, "Name", mode);
			this.session.put(this.sfProperty.getReservationResultFile(), filePath);

			if (this.isReservationCOUpsert()) {
				// For logging and waiting message
				logger.info("Optional : Saving ReservationCOs to file and upsert to Salesforce ...");
				this.addActionMessage("Optional : Saving ReservationCOs to file and upsert to Salesforce ...");
	
				// Upsert Reservation records related to the Accounts.
				filePath = ptl.saveReservationCOsToFile(fileDir + "Log_RevCO_" + fileName, reservationCOList);
				sf.upsertToSF("Reservation__c", authorization, userName, password, filePath, "Name", mode);
				this.session.put(this.sfProperty.getReservationCOResultFile(), filePath);
			}
			
			// For logging and waiting message
			logger.info("Steps 5/6, Saving Transactions to file and upsert to Salesforce ...");
			this.addActionMessage("Steps 5/6, Saving Transactions to file and upsert to Salesforce ...");

			// Upsert Transaction records related to the Accounts.
			filePath = ptl.saveTransactionsToFile(fileDir + "Log_Tran_" + fileName, transactionList);
			sf.upsertToSF("Transaction__c", authorization, userName, password, filePath, "Transaction_Number__c", mode);
			this.session.put(this.sfProperty.getTransactionResultFile(), filePath);

			// For logging and waiting message
			timeStamp = System.currentTimeMillis();
			logger.info("Steps 6/6, Total completed. Time consumed (ms) : " + (timeStamp - now.getTime()));
			this.addActionMessage("Steps 6/6, Total completed. Time consumed (ms) : " + (timeStamp - now.getTime()));

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
    		}
    		
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
		
		if (this.transactionBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.transactionBo = (TransactionBo) cxt.getBean("transactionBo");
		}
		
		if (this.reservationBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.reservationBo = (ReservationBo) cxt.getBean("reservationBo");
		}
		
		if (this.reservationCOBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.reservationCOBo = (ReservationCOBo) cxt.getBean("reservationCOBo");
		}
		
		if (this.kundenBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.kundenBo = (KundenBo) cxt.getBean("kundenBo");
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

	public Date getDepBegin() {
		return depBegin;
	}

	public void setDepBegin(Date depBegin) {
		this.depBegin = depBegin;
	}

	public Date getDepEnd() {
		return depEnd;
	}

	public void setDepEnd(Date depEnd) {
		this.depEnd = depEnd;
	}

	public boolean isReservationCOUpsert() {
		return reservationCOUpsert;
	}

	public void setReservationCOUpsert(boolean reservationCOUpsert) {
		this.reservationCOUpsert = reservationCOUpsert;
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