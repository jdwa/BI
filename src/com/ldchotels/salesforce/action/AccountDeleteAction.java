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
import com.ldchotels.util.PropertyBean;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.sforce.async.*;
import com.sforce.ws.ConnectionException;

public class AccountDeleteAction extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(AccountDeleteAction.class.getName());

	private Map<String, Object> session;
	private PropertyBean propertyBean;
	private Date delChgBegin;
	private Date delChgEnd;
	private boolean fileDelete = true;
	private boolean serial = true;
	
	public AccountDeleteAction() {
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
				
			SalesForceBo sf = new SalesForceBoImpl(propertyBean);
			String authorization = propertyBean.getAuthorization();
			String userName = propertyBean.getUserName();
			String password = propertyBean.getPassword();	
			String fileDir = propertyBean.getFileDir();
			String filePath = fileDir + "Log_Del_Pro_" + fileName;

			// For logging and waiting message
			logger.info("Steps 1/3, Query Accounts from Salesforce and save to local file ...");
			this.addActionMessage("Steps 1/3, Query Accounts from Salesforce and save to local file ...");
			
			// Query Transactions from Salesforce.
			String delChgBegin = "1900-01-01";
			if (delChgBegin != null) {
				delChgBegin = sdf.format(this.getDelChgBegin());
			}
			String delChgEnd = "1900-01-01";
			if (delChgEnd != null) {
				delChgEnd = sdf.format(this.getDelChgEnd());
			}
			sf.doBulkQueryAccountForDelete(authorization, userName, password, filePath, delChgBegin, delChgEnd);
			
			// For logging and waiting message
			logger.info("Steps 2/3, Delete Accounts in Salesforce accroding to local file ..." );
			logger.info("Criteria : Accounts last modified date between [" + delChgBegin + "T00:00:00Z] and [" + delChgEnd + "T23:59:59Z]" );
			this.addActionMessage("Steps 2/3, Delete Accounts in Salesforce accroding to local file ...");
			this.addActionMessage("Criteria : Accounts last modified date between [" + delChgBegin + "T00:00:00Z] and [" + delChgEnd + "T23:59:59Z]");

			// Delete Accounts in Salesforce.
			ConcurrencyMode mode = (this.isSerial() ? ConcurrencyMode.Serial : ConcurrencyMode.Parallel);
			sf.deleteInSF("Account", authorization, userName, password, filePath, "Guest_Account_No__c", mode);
			this.session.put(this.propertyBean.getAccountResultFile(), filePath);
			
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
    		File outputFile = new File(this.session.get(this.propertyBean.getAccountResultFile()).toString());
    		if (outputFile.exists() && outputFile.delete()){
    			logger.info("File deleted : " + outputFile.getAbsolutePath());
    		};
    		
    		outputFile = new File(this.session.get(this.propertyBean.getReservationResultFile()).toString());
    		if (outputFile.exists() && outputFile.delete()){
    			logger.info("File deleted : " + outputFile.getAbsolutePath());
    		};
    		
    		outputFile = new File(this.session.get(this.propertyBean.getReservationCOResultFile()).toString());
    		if (outputFile.exists() && outputFile.delete()){
    			logger.info("File deleted : " + outputFile.getAbsolutePath());
    		};    		
    		outputFile = new File(this.session.get(this.propertyBean.getTransactionResultFile()).toString());
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
		if (this.propertyBean == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.propertyBean = (PropertyBean) cxt.getBean("propertyBean");
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

	public Date getDelChgBegin() {
		return delChgBegin;
	}

	public void setDelChgBegin(Date delChgBegin) {
		this.delChgBegin = delChgBegin;
	}

	public Date getDelChgEnd() {
		return delChgEnd;
	}

	public void setDelChgEnd(Date delChgEnd) {
		this.delChgEnd = delChgEnd;
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