package com.ldchotels.edm.scheduler.jobs;
 
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.ldchotels.edm.controller.ChristmasEdmSender;
import com.ldchotels.util.EdmProperty;

public class ChristmasEdmSendScheduledJob extends QuartzJobBean {
	
	private static Logger logger = Logger.getLogger(ChristmasEdmSendScheduledJob.class.getName());
	private EdmProperty edmProperty;
	
	@Override
	protected void executeInternal(JobExecutionContext ctx)	throws JobExecutionException {
		logger.info("[ChristmasEdmSendScheduledJob][executeInternal]");
		try {
			ChristmasEdmSender edmSender = new ChristmasEdmSender(edmProperty.getChristmasEdmSubject(), 
					edmProperty.getChristmasEdmUrl(), edmProperty.getChristmasEdmList(), 
					edmProperty.isChristmasReadFile(), edmProperty.isChristmasReadDB(), 
					edmProperty.isChristmasActiveSend(), edmProperty.getSleepMillisecond());
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
