package com.ldchotels.edm.scheduler.jobs;
 
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.ldchotels.edm.controller.BossEdmSender;
import com.ldchotels.util.EdmProperty;

public class BossEdmSendScheduledJob extends QuartzJobBean {
	
	private static Logger logger = Logger.getLogger(BossEdmSendScheduledJob.class.getName());
	private EdmProperty edmProperty;
	
	@Override
	protected void executeInternal(JobExecutionContext ctx)	throws JobExecutionException {
		logger.info("[BossEdmSendScheduledJob][executeInternal]");
		try {
			BossEdmSender edmSender = new BossEdmSender(edmProperty.getBossEdmSubject(), 
					edmProperty.getBossEdmUrl(), edmProperty.getBossEdmList(), 
					edmProperty.isBossReadFile(), edmProperty.isBossReadDB(), 
					edmProperty.isBossActiveSend(), edmProperty.getSleepMillisecond());
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
