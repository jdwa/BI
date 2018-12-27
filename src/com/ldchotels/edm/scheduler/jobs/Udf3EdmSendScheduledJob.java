package com.ldchotels.edm.scheduler.jobs;
 
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.ldchotels.edm.controller.Udf3EdmSender;
import com.ldchotels.util.EdmProperty;

public class Udf3EdmSendScheduledJob extends QuartzJobBean {
	
	private static Logger logger = Logger.getLogger(Udf3EdmSendScheduledJob.class.getName());
	private EdmProperty edmProperty;
	
	@Override
	protected void executeInternal(JobExecutionContext ctx)	throws JobExecutionException {
		logger.info("[Udf3EdmSendScheduledJob][executeInternal]");
		try {
			Udf3EdmSender edmSender = new Udf3EdmSender(edmProperty.getUdf3EdmSubject(), 
					edmProperty.getUdf3EdmUrl(), edmProperty.getUdf3EdmList(), 
					edmProperty.isUdf3ReadFile(), edmProperty.isUdf3ReadDB(), 
					edmProperty.isUdf3ActiveSend(), edmProperty.getSleepMillisecond());
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
