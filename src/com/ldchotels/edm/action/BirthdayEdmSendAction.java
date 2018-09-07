package com.ldchotels.edm.action;

import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.ldchotels.edm.controller.BirthdayEdmSender;
import com.ldchotels.util.EdmProperty;

public class BirthdayEdmSendAction extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	private Map<String, Object> session;
	private EdmProperty edmProperty;
	
	public BirthdayEdmSendAction() {
		super();
	}

	/* ActionSupport */
	@Override
	public String execute() throws Exception {
		String returnValue = SUCCESS;
		BirthdayEdmSender edmSender = new BirthdayEdmSender(edmProperty.getBirthdayEdmSubject(), 
				edmProperty.getBirthdayEdmUrl(), edmProperty.getBirthdayEdmList(), 
				edmProperty.isBirthdayReadFile(), edmProperty.isBirthdayReadDB(), 
				edmProperty.isBirthdayActiveSend(), edmProperty.getSleepMillisecond());
		edmSender.setAction(this);
		edmSender.run();
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
		if (this.edmProperty == null) {
			WebApplicationContext cxt = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			this.edmProperty = (EdmProperty) cxt.getBean("edmProperty");
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
