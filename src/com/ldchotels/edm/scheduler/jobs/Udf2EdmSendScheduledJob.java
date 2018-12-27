package com.ldchotels.edm.scheduler.jobs;
 
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.ldchotels.edm.controller.Udf2EdmSender;
import com.ldchotels.util.EdmProperty;

public class Udf2EdmSendScheduledJob extends QuartzJobBean {
	
	private static Logger logger = Logger.getLogger(Udf2EdmSendScheduledJob.class.getName());
	private EdmProperty edmProperty;
	
	@Override
	protected void executeInternal(JobExecutionContext ctx)	throws JobExecutionException {
		logger.info("[Udf2EdmSendScheduledJob][executeInternal]");
		try {
			Udf2EdmSender edmSender = new Udf2EdmSender(edmProperty.getUdf2EdmSubject(), 
					edmProperty.getUdf2EdmUrl(), edmProperty.getUdf2EdmList(), 
					edmProperty.isUdf2ReadFile(), edmProperty.isUdf2ReadDB(), 
					edmProperty.isUdf2ActiveSend(), edmProperty.getSleepMillisecond());
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
