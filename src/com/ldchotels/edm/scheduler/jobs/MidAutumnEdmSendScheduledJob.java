package com.ldchotels.edm.scheduler.jobs;
 
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.ldchotels.edm.controller.MidAutumnEdmSender;
import com.ldchotels.util.EdmProperty;

public class MidAutumnEdmSendScheduledJob extends QuartzJobBean {
	
	private static Logger logger = Logger.getLogger(MidAutumnEdmSendScheduledJob.class.getName());
	private EdmProperty edmProperty;
	
	@Override
	protected void executeInternal(JobExecutionContext ctx)	throws JobExecutionException {
		logger.info("[MidAutumnEdmSendScheduledJob][executeInternal]");
		try {
			MidAutumnEdmSender edmSender = new MidAutumnEdmSender(edmProperty.getMidAutumnEdmSubject(), 
					edmProperty.getMidAutumnEdmUrl(), edmProperty.getMidAutumnEdmList(), 
					edmProperty.isMidAutumnReadFile(), edmProperty.isMidAutumnReadDB(), 
					edmProperty.isMidAutumnActiveSend(), edmProperty.getSleepMillisecond());
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
