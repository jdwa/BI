package com.ldchotels.edm.action;

import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.ldchotels.edm.controller.HolidayEdmSender;
import com.ldchotels.util.EdmProperty;

public class ChristmasEdmSendAction extends ActionSupport implements Preparable, SessionAware {
	private static final long serialVersionUID = 1L;
	private Map<String, Object> session;
	private EdmProperty edmProperty;
	
	public ChristmasEdmSendAction() {
		super();
	}

	/* ActionSupport */
	@Override
	public String execute() throws Exception {
		String returnValue = SUCCESS;
		HolidayEdmSender edmSender = new HolidayEdmSender(edmProperty.getChristmasEdmSubject(), 
				edmProperty.getChristmasEdmUrl(), edmProperty.getChristmasEdmList(), 
				edmProperty.isChristmasReadFile(), edmProperty.isChristmasReadDB(), 
				edmProperty.isChristmasActiveSend(), edmProperty.getSleepMillisecond());
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
