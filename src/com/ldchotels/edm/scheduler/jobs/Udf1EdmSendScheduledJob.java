package com.ldchotels.edm.scheduler.jobs;
 
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.ldchotels.edm.controller.Udf1EdmSender;
import com.ldchotels.util.EdmProperty;

public class Udf1EdmSendScheduledJob extends QuartzJobBean {
	
	private static Logger logger = Logger.getLogger(Udf1EdmSendScheduledJob.class.getName());
	private EdmProperty edmProperty;
	
	@Override
	protected void executeInternal(JobExecutionContext ctx)	throws JobExecutionException {
		logger.info("[Udf1EdmSendScheduledJob][executeInternal]");
		try {
			Udf1EdmSender edmSender = new Udf1EdmSender(edmProperty.getUdf1EdmSubject(), 
					edmProperty.getUdf1EdmUrl(), edmProperty.getUdf1EdmList(), 
					edmProperty.isUdf1ReadFile(), edmProperty.isUdf1ReadDB(), 
					edmProperty.isUdf1ActiveSend(), edmProperty.getSleepMillisecond());
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
