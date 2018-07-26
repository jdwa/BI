package com.ldchotels.salesforce.action;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ldchotels.protel.bo.ProtelBo;
import com.ldchotels.protel.bo.ProtelBoImpl;
import com.ldchotels.protel.bo.ReservationBo;
import com.ldchotels.protel.model.Reservation;
import com.ldchotels.salesforce.bo.SalesForceBo;
import com.ldchotels.salesforce.bo.SalesForceBoImpl;
import com.ldchotels.util.PropertyBean;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.sforce.async.*;
import com.sforce.ws.ConnectionException;

public class ReservationUpsertAction extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ReservationUpsertAction.class.getName());

	private Map<String, Object> session;
	private PropertyBean propertyBean;
	private ReservationBo reservationBo;
	private List<Reservation> reservationList; // For List action
	private Date arrBegin;
	private Date arrEnd;
	private boolean fileDelete = true;
	private boolean serial = true;

	public ReservationUpsertAction() {
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
			
			// Get Protel reservations list
			String arrBegin = "1900-01-01";
			if (arrBegin != null) {
				arrBegin = sdf.format(this.getArrBegin());
			}
			String arrEnd = "1900-01-01";
			if (arrEnd != null) {
				arrEnd = sdf.format(this.getArrEnd());
			}
			
			logger.info("Criteria : Reservations arrival between [" + arrBegin + "T00:00:00Z] and [" + arrEnd + "T23:59:59Z]");
			this.addActionMessage("Criteria : Reservations arrival between [" + arrBegin + "T00:00:00Z] and [" + arrEnd + "T23:59:59Z]");

			logger.info("Steps 1/3, Getting Reservations from Protel ...");
			this.addActionMessage("Steps 1/3, Getting Reservations from Protel ...");
			
			ProtelBo ptl = new ProtelBoImpl(this.propertyBean, this.reservationBo);
			reservationList = ptl.getReservationsFromProtel(arrBegin, arrEnd);	
			
			// For logging and waiting message			
			long timeStamp = System.currentTimeMillis();
			logger.info("Completed. Total Reservations : " + reservationList.size() + ", Time consumed (ms): " + (timeStamp - now.getTime()));
			this.addActionMessage("Completed. Total Reservations : " + reservationList.size() + ", Time consumed (ms): " + (timeStamp - now.getTime()));
								
			SalesForceBo sf = new SalesForceBoImpl(propertyBean);
			String authorization = propertyBean.getAuthorization();
			String userName = propertyBean.getUserName();
			String password = propertyBean.getPassword();
			String fileDir = propertyBean.getFileDir();

			// For logging and waiting message
			logger.info("Steps 2/3, Saving Reservations to file and upsert to Salesforce ...");
			this.addActionMessage("Steps 2/3, Saving Reservations to file and upsert to Salesforce ...");
			
			String filePath = ptl.saveReservationsToFile(fileDir + "Log_Rev_" + fileName, reservationList);
			
			logger.info("Saving Reservations to file : [" + filePath + "] completed.");
			this.addActionMessage("Saving Reservations to file : [" + filePath + "] completed.");
			
			logger.info("Upsert Reservations to Salesforce ...");
			this.addActionMessage("Upsert Reservations to Salesforce ...");
			
			// Upsert Reservation records.
			ConcurrencyMode mode = (this.isSerial() ? ConcurrencyMode.Serial : ConcurrencyMode.Parallel);
			sf.upsertToSF("Reservation__c", authorization, userName, password, filePath, "Name", mode);
			this.session.put(this.propertyBean.getReservationResultFile(), filePath);
			
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
		
		if (this.reservationBo == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.reservationBo = (ReservationBo) cxt.getBean("reservationBo");
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

	public Date getArrBegin() {
		return arrBegin;
	}

	public void setArrBegin(Date arrBegin) {
		this.arrBegin = arrBegin;
	}

	public Date getArrEnd() {
		return arrEnd;
	}

	public void setArrEnd(Date arrEnd) {
		this.arrEnd = arrEnd;
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