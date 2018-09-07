package com.ldchotels.edm.scheduler.jobs;
 
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.ldchotels.edm.controller.MiddleMoonEdmSender;
import com.ldchotels.util.EdmProperty;

public class MiddleMoonEdmSendScheduledJob extends QuartzJobBean {
	
	private static Logger logger = Logger.getLogger(MiddleMoonEdmSendScheduledJob.class.getName());
	private EdmProperty edmProperty;
	
	@Override
	protected void executeInternal(JobExecutionContext ctx)	throws JobExecutionException {
		logger.info("[MiddleMoonEdmSendScheduledJob][executeInternal]");
		try {
			MiddleMoonEdmSender edmSender = new MiddleMoonEdmSender(edmProperty.getMiddleMoonEdmSubject(), 
					edmProperty.getMiddleMoonEdmUrl(), edmProperty.getMiddleMoonEdmList(), 
					edmProperty.isMiddleMoonReadFile(), edmProperty.isMiddleMoonReadDB(), 
					edmProperty.isMiddleMoonActiveSend(), edmProperty.getSleepMillisecond());
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
