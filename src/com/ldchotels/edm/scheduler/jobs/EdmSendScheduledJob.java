package com.ldchotels.edm.scheduler.jobs;
 
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.ldchotels.edm.controller.EdmSender;
import com.ldchotels.util.PropertyBean;

public class EdmSendScheduledJob extends QuartzJobBean {
	
	private static Logger logger = Logger.getLogger(EdmSendScheduledJob.class.getName());
	private PropertyBean propertyBean;
	
	@Override
	protected void executeInternal(JobExecutionContext ctx)	throws JobExecutionException {

		try {
			EdmSender edmSender;
			edmSender = new EdmSender(propertyBean);
			edmSender.start();
		} catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
	}

	public PropertyBean getPropertyBean() {
		return propertyBean;
	}

	public void setPropertyBean(PropertyBean propertyBean) {
		this.propertyBean = propertyBean;
	}
}
