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
import com.ldchotels.protel.model.Kunden;
import com.ldchotels.salesforce.bo.SalesForceBo;
import com.ldchotels.salesforce.bo.SalesForceBoImpl;
import com.ldchotels.util.PropertyBean;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.sforce.async.*;
import com.sforce.ws.ConnectionException;

public class AccountUpsertAction extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(AccountUpsertAction.class.getName());

	private Map<String, Object> session;
	private PropertyBean propertyBean;
	private KundenBo kundenBo;
	private List<Kunden> kundenList; // For List action
	private Date chgBegin;
	private Date chgEnd;
	private boolean fileDelete = true;
	private boolean serial = true;

	public AccountUpsertAction() {
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
	
			// Get Protel kunden list
			String chgBegin = "1900-01-01";
			if (chgBegin != null) {
				chgBegin = sdf.format(this.getChgBegin());
			}
			String chgEnd = "1900-01-01";
			if (chgEnd != null) {
				chgEnd = sdf.format(this.getChgEnd());
			}
			
			logger.info("Criteria : Kundens changed between [" + chgBegin + "T00:00:00Z] and [" + chgEnd + "T23:59:59Z]");
			this.addActionMessage("Criteria : Kundens changed between [" + chgBegin + "T00:00:00Z] and [" + chgEnd + "T23:59:59Z]");

			logger.info("Steps 1/3, Getting Kundens from Protel ...");
			this.addActionMessage("Steps 1/3, Getting Kundens from Protel ...");
			
			ProtelBo ptl = new ProtelBoImpl(this.propertyBean, this.kundenBo);
			kundenList = ptl.getKundensFromProtel(chgBegin, chgEnd);

			long timeStamp = System.currentTimeMillis();
			logger.info("Completed. Total Kundens : " + kundenList.size() + ", Time consumed (ms): " + (timeStamp - now.getTime()));
			this.addActionMessage("Completed. Total Kundens : " + kundenList.size() + ", Time consumed (ms): " + (timeStamp - now.getTime()));

			// For logging and waiting message
			logger.info("Completed. Total Kundens : " + kundenList.size());
			this.addActionMessage("Completed. Total Kundens : " + kundenList.size());
			
			SalesForceBo sf = new SalesForceBoImpl(propertyBean);
			String authorization = propertyBean.getAuthorization();
			String userName = propertyBean.getUserName();
			String password = propertyBean.getPassword();
			String fileDir = propertyBean.getFileDir();

			// For logging and waiting message
			logger.info("Steps 2/3, Saving Accounts to file and upsert to Salesforce ...");
			this.addActionMessage("Steps 2/3, Saving Accounts to file and upsert to Salesforce ...");
			
			String filePath = ptl.saveKundensToFile(fileDir + "Log_Pro_" + fileName, kundenList);
			
			logger.info("Saving Accounts to file : [" + filePath + "] completed.");
			this.addActionMessage("Saving Accounts to file : [" + filePath + "] completed.");
			
			logger.info("Upsert Accounts to Salesforce ...");
			this.addActionMessage("Upsert Accounts to Salesforce ...");
			
			// Upsert Account records.
			ConcurrencyMode mode = (this.isSerial() ? ConcurrencyMode.Serial : ConcurrencyMode.Parallel);
			sf.upsertToSF("Account", authorization, userName, password, filePath, "Guest_Profile_No__c", mode);
			this.session.put(this.propertyBean.getAccountResultFile(), filePath);
			
			// For logging and waiting message
			timeStamp = System.currentTimeMillis();
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

	public Date getChgBegin() {
		return chgBegin;
	}

	public void setChgBegin(Date chgBegin) {
		this.chgBegin = chgBegin;
	}

	public Date getChgEnd() {
		return chgEnd;
	}

	public void setChgEnd(Date chgEnd) {
		this.chgEnd = chgEnd;
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