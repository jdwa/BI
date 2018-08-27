package com.ldchotels.edm.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ldchotels.edm.http.WebPageReader;
import com.ldchotels.edm.mail.thread.MailSender;
import com.ldchotels.util.PropertyBean;

public class EdmSender extends java.lang.Thread {
	private static Logger logger = Logger.getLogger(EdmSender.class.getName());
	
	private PropertyBean propertyBean;

	public EdmSender(PropertyBean propertyBean) throws Exception {
		super();
		this.propertyBean = propertyBean;	
	}

	public void run() {
		FileReader fis = null;
		BufferedReader bis = null;
		ArrayList<String> toList = new ArrayList<String>();
		String subject = this.propertyBean.getEdmSubject();
		String content = "*** Edm content not yet ready ! ***";

		try {
			// 名單
			fis = new FileReader(this.propertyBean.getEdmList());
			bis = new BufferedReader(fis);

			String str;
			while ((str = bis.readLine()) != null) {
				toList.add(str.trim());
			}
			fis.close();
			bis.close();
			fis = null;
			bis = null;

			// 取得網頁內容
			WebPageReader web = new WebPageReader();
			if (web.doGet(this.propertyBean.getEdmUrl(), null, null, "utf-8")) {
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
				MailSender sender = new MailSender(toList.get(i), subject, content, propertyBean);
				sender.start();
				// 暫停數秒後再開始寄信
				Thread.sleep(this.propertyBean.getMailSleep());

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
