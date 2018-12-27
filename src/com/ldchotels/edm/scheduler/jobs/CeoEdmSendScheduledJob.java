package com.ldchotels.edm.scheduler.jobs;
 
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.ldchotels.edm.controller.CeoEdmSender;
import com.ldchotels.util.EdmProperty;

public class CeoEdmSendScheduledJob extends QuartzJobBean {
	
	private static Logger logger = Logger.getLogger(CeoEdmSendScheduledJob.class.getName());
	private EdmProperty edmProperty;
	
	@Override
	protected void executeInternal(JobExecutionContext ctx)	throws JobExecutionException {
		logger.info("[CeoEdmSendScheduledJob][executeInternal]");
		try {
			CeoEdmSender edmSender = new CeoEdmSender(edmProperty.getCeoEdmSubject(), 
					edmProperty.getCeoEdmUrl(), edmProperty.getCeoEdmList(), 
					edmProperty.isCeoReadFile(), edmProperty.isCeoReadDB(), 
					edmProperty.isCeoActiveSend(), edmProperty.getSleepMillisecond());
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
