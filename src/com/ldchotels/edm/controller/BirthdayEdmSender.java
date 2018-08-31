package com.ldchotels.edm.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.ldchotels.athena.dao.EmployeeDao;
import com.ldchotels.athena.model.Employee;
import com.ldchotels.edm.http.WebPageReader;
import com.ldchotels.edm.mail.thread.MailSender;
import com.ldchotels.util.ApplicationContextProvider;
import com.ldchotels.util.EdmProperty;

public class BirthdayEdmSender extends java.lang.Thread {
	private static Logger logger = Logger.getLogger(BirthdayEdmSender.class.getName());
	
	private EdmProperty edmProperty;

	public BirthdayEdmSender() throws Exception {
		super();
		ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
		this.edmProperty = (EdmProperty) ctx.getBean("edmProperty");
	}

	public void run() {
		ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
		FileReader fis = null;
		BufferedReader bis = null;
		ArrayList<String> toList = new ArrayList<String>();
		String subject = this.edmProperty.getBirthdayEdmSubject();
		String content = "*** Edm content not yet ready ! ***";

		try {
			// 名單
			fis = new FileReader(this.edmProperty.getBirthdayEdmList());
			bis = new BufferedReader(fis);

			String str;
			while ((str = bis.readLine()) != null) {
				toList.add(str.trim());
			}
			fis.close();
			bis.close();
			fis = null;
			bis = null;

			EmployeeDao employeeDao;
			
			logger.info("********** Procrssing HQ ***********");
			employeeDao = (EmployeeDao) ctx.getBean("employeeHQDao");
			if (ctx.getBean("HQDataSource") != null) {
				List<Employee> employeeList = employeeDao.list();
				for(int i = 0 ; i < employeeList.size(); i++){
					System.out.println("==" + employeeList.get(i).getClass().getName() + "===" + employeeList.size() + "=====>" + employeeList.get(i).getPers_nam());
				}
			}

			logger.info("********** Procrssing SJ ***********");
			employeeDao = (EmployeeDao) ctx.getBean("employeeHQDao");
			if (ctx.getBean("HQDataSource") != null) {
				List<Employee> employeeList = employeeDao.list();
				for(int i = 0 ; i < employeeList.size(); i++){
					System.out.println("==" + employeeList.get(i).getClass().getName() + "===" + employeeList.size() + "=====>" + employeeList.get(i).getPers_nam());
				}
			}

			logger.info("********** Procrssing TY ***********");
			employeeDao = (EmployeeDao) ctx.getBean("employeeTYDao");
			if (ctx.getBean("TYDataSource") != null) {
				List<Employee> employeeList = employeeDao.list();
				for(int i = 0 ; i < employeeList.size(); i++){
					System.out.println("==" + employeeList.get(i).getClass().getName() + "===" + employeeList.size() + "=====>" + employeeList.get(i).getPers_nam());
				}
			}

			logger.info("********** Procrssing HL ***********");
			employeeDao = (EmployeeDao) ctx.getBean("employeeHLDao");
			if (ctx.getBean("HLDataSource") != null) {
				List<Employee> employeeList = employeeDao.list();
				for(int i = 0 ; i < employeeList.size(); i++){
					System.out.println("==" + employeeList.get(i).getClass().getName() + "===" + employeeList.size() + "=====>" + employeeList.get(i).getPers_nam());
				}
			}

			logger.info("********** Procrssing KH ***********");
			employeeDao = (EmployeeDao) ctx.getBean("employeeKHDao");
			if (ctx.getBean("KHDataSource") != null) {
				List<Employee> employeeList = employeeDao.list();
				for(int i = 0 ; i < employeeList.size(); i++){
					System.out.println("==" + employeeList.get(i).getClass().getName() + "===" + employeeList.size() + "=====>" + employeeList.get(i).getPers_nam());
				}
			}

			logger.info("********** Procrssing CY ***********");
			employeeDao = (EmployeeDao) ctx.getBean("employeeCYDao");
			if (ctx.getBean("CYDataSource") != null) {
				List<Employee> employeeList = employeeDao.list();
				for(int i = 0 ; i < employeeList.size(); i++){
					System.out.println("==" + employeeList.get(i).getClass().getName() + "===" + employeeList.size() + "=====>" + employeeList.get(i).getPers_nam());
				}
			}

			/*--
			logger.info("********** Procrssing PDC ***********");
			employeeDao = (EmployeeDao) ctx.getBean("employeePDCDao");
			if (ctx.getBean("PDCDataSource") != null) {
				List<Employee> employeeList = employeeDao.list();
				for(int i = 0 ; i < employeeList.size(); i++){
					System.out.println("==" + employeeList.get(i).getClass().getName() + "===" + employeeList.size() + "=====>" + employeeList.get(i).getPers_nam());
				}
			}
			--*/

			logger.info("********** Procrssing SML ***********");
			employeeDao = (EmployeeDao) ctx.getBean("employeeSMLDao");
			if (ctx.getBean("SMLDataSource") != null) {
				List<Employee> employeeList = employeeDao.list();
				for(int i = 0 ; i < employeeList.size(); i++){
					System.out.println("==" + employeeList.get(i).getClass().getName() + "===" + employeeList.size() + "=====>" + employeeList.get(i).getPers_nam());
				}
			}

			/*--
			logger.info("********** Procrssing YP ***********");
			employeeDao = (EmployeeDao) ctx.getBean("employeeYPDao");
			if (ctx.getBean("YPDataSource") != null) {
				List<Employee> employeeList = employeeDao.list();
				for(int i = 0 ; i < employeeList.size(); i++){
					System.out.println("==" + employeeList.get(i).getClass().getName() + "===" + employeeList.size() + "=====>" + employeeList.get(i).getPers_nam());
				}
			}
			--*/

			// 取得網頁內容
			WebPageReader web = new WebPageReader();
			if (web.doGet(this.edmProperty.getBirthdayEdmUrl(), null, null, "utf-8")) {
				content = web.getContent();
			} else {
				logger.error("Get web page error !");
			}		
		} catch (IOException ioex) {
			logger.error(ioex.getMessage());
		} finally {
			try {
				if (bis != null) {
					bis.close();
					bis = null;
				}

				if (fis != null) {
					fis.close();
					fis = null;
				}	
			} catch (IOException ioex) {
				logger.error(ioex.getMessage());
			}
		}

		FileWriter fw = null;
		try {
			for (int i = 0; i < toList.size(); i++) {
				// 啟動thread開始寄信
				logger.info("Send to : [" + toList.get(i) + "], Subject : [" + subject + "]");
				MailSender sender = new MailSender(toList.get(i), subject, content, edmProperty);
				sender.start();
				// 暫停數秒後再開始寄信
				Thread.sleep(this.edmProperty.getMailSleep());

				if (fw != null) {
					fw.flush();
					fw.write(toList.get(i) + "\r\n");
				}
			}
		} catch	(InterruptedException ie) {
			logger.error(ie.getMessage());
		} catch (IOException ioex) {
			logger.error(ioex.getMessage());
		} finally {
			if (fw != null) {
				try {
					fw.flush();
					fw.close();
					fw = null;
				} catch (IOException ioex) {
					logger.error(ioex.getMessage());
				}
			}
		}
	}
}
