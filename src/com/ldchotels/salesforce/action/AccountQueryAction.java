package com.ldchotels.salesforce.action;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

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

public class AccountQueryAction extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;

	private Map<String, Object> session;
	private SalesforceProperty sfProperty;
	
	public AccountQueryAction() {
		super();
	}

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
			String filePath = fileDir + "Rlt_Pro_" + fileName;
			sf.doBulkQueryAccount(authorization, userName, password, filePath);
			returnValue = SUCCESS;
		}
		
		return returnValue;
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
}