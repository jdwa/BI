package com.ldchotels.edm.scheduler.jobs;
 
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.ldchotels.edm.controller.BirthdayEdmSender;

public class BirthdayEdmSendScheduledJob extends QuartzJobBean {
	
	private static Logger logger = Logger.getLogger(BirthdayEdmSendScheduledJob.class.getName());
	
	@Override
	protected void executeInternal(JobExecutionContext ctx)	throws JobExecutionException {
		logger.info("[BirthdayEdmSendScheduledJob][executeInternal]");
		try {
			BirthdayEdmSender edmSender = new BirthdayEdmSender();
			edmSender.start();
		} catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
	}
}
