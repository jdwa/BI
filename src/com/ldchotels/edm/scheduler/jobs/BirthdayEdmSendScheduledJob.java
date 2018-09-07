package com.ldchotels.edm.scheduler.jobs;
 
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.ldchotels.edm.controller.BirthdayEdmSender;
import com.ldchotels.util.EdmProperty;

public class BirthdayEdmSendScheduledJob extends QuartzJobBean {
	
	private static Logger logger = Logger.getLogger(BirthdayEdmSendScheduledJob.class.getName());
	private EdmProperty edmProperty;
	
	@Override
	protected void executeInternal(JobExecutionContext ctx)	throws JobExecutionException {
		logger.info("[BirthdayEdmSendScheduledJob][executeInternal]");
		try {
			BirthdayEdmSender edmSender = new BirthdayEdmSender(edmProperty.getBirthdayEdmSubject(), 
					edmProperty.getBirthdayEdmUrl(), edmProperty.getBirthdayEdmList(), 
					edmProperty.isBirthdayReadFile(), edmProperty.isBirthdayReadDB(), 
					edmProperty.isBirthdayActiveSend(), edmProperty.getSleepMillisecond());
			edmSender.run();
		} catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public EdmProperty getEdmProperty() {
		return edmProperty;
	}

	public void setEdmProperty(EdmProperty edmProperty) {
		this.edmProperty = edmProperty;
	}
}
