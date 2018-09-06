package com.ldchotels.edm.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.ldchotels.athena.dao.EmployeeDao;
import com.ldchotels.athena.model.Employee;
import com.ldchotels.edm.http.WebPageReader;
import com.ldchotels.edm.mail.thread.MailSender;
import com.ldchotels.util.ApplicationContextProvider;

public class HolidayEdmSender extends java.lang.Thread {
	private static Logger logger = Logger.getLogger(HolidayEdmSender.class.getName());
	
	private String subject;
	private String edmUrl;
	private String edmList;
	private boolean isReadFile;
	private boolean isReadDB;
	private boolean isActive;
	private int sleepMillisecond;
	ArrayList<String> toList;


	public HolidayEdmSender(String subject, String edmUrl, String edmList, 
			boolean isReadFile, boolean isReadDB, boolean isActive, int sleepMillisecond) throws Exception {
		super();
		this.subject = subject;
		this.edmUrl = edmUrl;
		this.edmList = edmList;
		this.isReadFile = isReadFile;
		this.isReadDB = isReadDB;
		this.isActive = isActive;
		this.sleepMillisecond = sleepMillisecond;
		this.toList = new ArrayList<String>();
	}

	public void run() {
		//String subject = this.edmProperty.getBirthdayEdmSubject();
		String content = "*** Edm content not yet ready ! ***";

		if (this.isReadFile){
			this.toList.addAll(prepareFileList());
		}
		
		if (this.isReadDB){
			this.toList.addAll(prepareDBList());
		}

		// 取得網頁內容
		WebPageReader web = new WebPageReader();
		if (web.doGet(this.edmUrl, null, null, "utf-8")) {
			content = web.getContent();
		} else {
			logger.error("Get web page error !");
		}

		FileWriter fw = null;
		try {
			for (int i = 0; i < toList.size(); i++) {
				logger.info("Send to : [" + toList.get(i) + "], Subject : [" + subject + "]");
				if (this.isActive) {
					// 啟動thread開始寄信
					MailSender sender = new MailSender(toList.get(i), subject, content);
					sender.start();
					// 暫停數秒後再開始寄信
					Thread.sleep(this.sleepMillisecond);
				} else {
					logger.info("Send mode OFF, no EDM send!");
				}

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
	
	private ArrayList<String> prepareFileList() {
		ArrayList<String> list = new ArrayList<String>();
		FileReader fis = null;
		BufferedReader bis = null;

		try {
			// 名單
			fis = new FileReader(this.edmList);
			bis = new BufferedReader(fis);

			String str;
			while ((str = bis.readLine()) != null) {
				list.add(str.trim());
			}
			fis.close();
			bis.close();
			fis = null;
			bis = null;
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
		return list;
	}
	
	private ArrayList<String> prepareDBList() {
		ArrayList<String> list = new ArrayList<String>();
		EmployeeDao employeeDao;
		Calendar now = Calendar.getInstance();
        HashMap<String, Object> dbMap = new HashMap<String, Object>();
        
        ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
        dbMap.put("HQ", ctx.getBean("employeeHQDao"));
        dbMap.put("SJ", ctx.getBean("employeeSJDao"));
        dbMap.put("TY", ctx.getBean("employeeTYDao"));
        dbMap.put("HL", ctx.getBean("employeeHLDao"));
        dbMap.put("KH", ctx.getBean("employeeKHDao"));
        dbMap.put("CY", ctx.getBean("employeeCYDao"));
        dbMap.put("PDC", ctx.getBean("employeePDCDao"));
        dbMap.put("SML", ctx.getBean("employeeSMLDao"));
        dbMap.put("YP", ctx.getBean("employeeYPDao"));
        
        for (Object key : dbMap.keySet()) {
    		try {
    			logger.info("********** Procrssing " + key +" ***********");
    			employeeDao = (EmployeeDao) dbMap.get(key);
    			if (employeeDao != null) {
    				List<Employee> employeeList = employeeDao.employedList(now.getTime());
    				logger.info("Currently total employed : [" + employeeList.size() + "]");
    				for(int i = 0 ; i < employeeList.size(); i++){
    					list.add(employeeList.get(i).getMail_addr().trim());
    				}
    			}
    		} catch(Exception e) {
    			logger.error(e.getMessage());
    		}
        }
		
 		return list;
	}
}
