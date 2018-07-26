package com.ldchotels.salesforce.action;

import java.io.*;
import java.util.*;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ldchotels.util.PropertyBean;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class ResultFilesDownloadAction extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ResultFilesDownloadAction.class.getName());

	private Map<String, Object> session;
	private InputStream inputStream;
	private PropertyBean propertyBean;

	public ResultFilesDownloadAction() {
		super();
	}

	/* ActionSupport */
	@Override
	public String execute() throws Exception {
		return super.execute();
	}
	
	public String resultAccountsDolnload() throws Exception {
		String returnValue = ERROR;	
		if (this.session != null) {
			if (this.session.get(this.propertyBean.getAccountResultFile()) != null) {
				File outputFile = new File(this.session.get(this.propertyBean.getAccountResultFile()).toString());	
				if (outputFile.exists()) {
					inputStream = new FileInputStream(outputFile);
					returnValue = SUCCESS;
				} else {
					this.addActionMessage(getText("errors.no.such.file"));
					returnValue = INPUT;					
				}
			} else {
				this.addActionMessage(getText("errors.no.such.file"));
				returnValue = INPUT;
			}
		}
		return returnValue;
	}

	public String resultReservationsDolnload() throws Exception {
		String returnValue = ERROR;	
		if (this.session != null) {
			if (this.session.get(this.propertyBean.getReservationResultFile()) != null) {
				File outputFile = new File(this.session.get(this.propertyBean.getReservationResultFile()).toString());
				if (outputFile.exists()) {
					inputStream = new FileInputStream(outputFile);
					returnValue = SUCCESS;
				} else {
					this.addActionMessage(getText("errors.no.such.file"));
					returnValue = INPUT;					
				}
			} else {
				this.addActionMessage(getText("errors.no.such.file"));
				returnValue = INPUT;
			}
		}
		return returnValue;
	}
	
	public String resultReservationCOsDolnload() throws Exception {
		String returnValue = ERROR;	
		if (this.session != null) {
			if (this.session.get(this.propertyBean.getReservationCOResultFile()) != null) {
				File outputFile = new File(this.session.get(this.propertyBean.getReservationCOResultFile()).toString());
				if (outputFile.exists()) {
					inputStream = new FileInputStream(outputFile);
					returnValue = SUCCESS;
				} else {
					this.addActionMessage(getText("errors.no.such.file"));
					returnValue = INPUT;					
				}
			} else {
				this.addActionMessage(getText("errors.no.such.file"));
				returnValue = INPUT;
			}
		}
		return returnValue;
	}
	
	public String resultTransactionsDolnload() throws Exception {
		String returnValue = ERROR;	
		if (this.session != null) {
			if (this.session.get(this.propertyBean.getTransactionResultFile()) != null) {
				File outputFile = new File(this.session.get(this.propertyBean.getTransactionResultFile()).toString());	
				if (outputFile.exists()) {
					inputStream = new FileInputStream(outputFile);
					returnValue = SUCCESS;
				} else {
					this.addActionMessage(getText("errors.no.such.file"));
					returnValue = INPUT;
				}
			} else {
				this.addActionMessage(getText("errors.no.such.file"));
				returnValue = INPUT;
			}
		}
		return returnValue;
	}
	
    protected void finalize(){
    	if ((this.session != null) && (this.propertyBean.isFileDelete())) {
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
	
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}