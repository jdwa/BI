package com.ldchotels.edm.scheduler.jobs;
 
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.ldchotels.edm.controller.HolidayEdmSender;
import com.ldchotels.util.EdmProperty;

public class FathersDayEdmSendScheduledJob extends QuartzJobBean {
	
	private static Logger logger = Logger.getLogger(FathersDayEdmSendScheduledJob.class.getName());
	private EdmProperty edmProperty;
	
	@Override
	protected void executeInternal(JobExecutionContext ctx)	throws JobExecutionException {
		logger.info("[FathersDayEdmSendScheduledJob][executeInternal]");
		try {
			HolidayEdmSender edmSender = new HolidayEdmSender(edmProperty.getFathersDayEdmSubject(), 
					edmProperty.getFathersDayEdmUrl(), edmProperty.getFathersDayEdmList(), 
					edmProperty.isFathersDayReadFile(), edmProperty.isFathersDayReadDB(), 
					edmProperty.isFathersDayActiveSend(), edmProperty.getSleepMillisecond());
			edmSender.start();
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
