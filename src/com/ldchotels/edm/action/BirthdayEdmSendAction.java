package com.ldchotels.edm.action;

import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.ldchotels.edm.controller.BirthdayEdmSender;
import com.ldchotels.util.SalesforceProperty;

public class BirthdayEdmSendAction extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	private Map<String, Object> session;
	private SalesforceProperty sfProperty;

	public BirthdayEdmSendAction() {
		super();
	}

	/* ActionSupport */
	@Override
	public String execute() throws Exception {
		String returnValue = SUCCESS;
		BirthdayEdmSender edmSender = new BirthdayEdmSender();
		edmSender.start();
		
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
