package com.ldchotels.edm.scheduler.jobs;
 
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.ldchotels.edm.controller.DragonBoatEdmSender;
import com.ldchotels.util.EdmProperty;

public class DragonBoatEdmSendScheduledJob extends QuartzJobBean {
	
	private static Logger logger = Logger.getLogger(DragonBoatEdmSendScheduledJob.class.getName());
	private EdmProperty edmProperty;
	
	@Override
	protected void executeInternal(JobExecutionContext ctx)	throws JobExecutionException {
		logger.info("[DragonBoatEdmSendScheduledJob][executeInternal]");
		try {
			DragonBoatEdmSender edmSender = new DragonBoatEdmSender(edmProperty.getDragonBoatEdmSubject(), 
					edmProperty.getDragonBoatEdmUrl(), edmProperty.getDragonBoatEdmList(), 
					edmProperty.isDragonBoatReadFile(), edmProperty.isDragonBoatReadDB(), 
					edmProperty.isDragonBoatActiveSend(), edmProperty.getSleepMillisecond());
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
