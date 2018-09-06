package com.ldchotels.edm.mail.thread;

import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.ldchotels.util.ApplicationContextProvider;
import com.ldchotels.util.MailProperty;

public class MailSender extends java.lang.Thread {
	private static Logger logger = Logger.getLogger(MailSender.class.getName());

	// 郵件標題
	private String subject;
	// 寄件對向
	private String to;
	// 內容
	private String content;
	// 設定檔
	private MailProperty mailProperty;


	// 建構
	public MailSender(String to, String subject, String content) {
		super();
		ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
		this.mailProperty = (MailProperty) ctx.getBean("mailProperty");
		this.to = to;
		this.subject = subject;
		this.content = content;
	}

	// thread run
	public void run() {
		try {

			// 設定傳送基本資訊
			// 取得mail host名稱
			String host = mailProperty.getMailHost();
			// 取得寄信者mail
			String from = mailProperty.getMailFrom();
			// 取得寄信者帳號
			String user = mailProperty.getMailUser();
			// 取得寄信者帳號密碼
			String pwd = mailProperty.getMailPwd();

			Properties props = System.getProperties();
			// 設定SMTP server
			props.put("mail.smtp.host", host);
			// 是否需要認證
			props.put("mail.smtp.auth", "true");
			// 依據Properties建立一個Session
			Session mailSession = Session.getInstance(props, null);
			// 從Session建立一個Message
			MimeMessage message = new MimeMessage(mailSession);
			// 設定mail From
			message.setFrom(new InternetAddress(from, mailProperty.getMailSender(), "utf-8"));
			// 設定mail To
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			// 設定標題
			message.setSubject(this.subject, "utf-8");
			// 設定寄件日期
			message.setSentDate(new Date());
			// 建立郵件本文內容
			Multipart multipart = new MimeMultipart();
			// 郵件內容
			BodyPart contentPart = new MimeBodyPart();
			// 給BodyPart對像設置內容和格式/編碼方式
			contentPart.setContent(content, "text/html;charset=utf8");
			// 加入郵件內容到mutipart裡
			multipart.addBodyPart(contentPart);
			// 將multipart加到mail的message裡
			message.setContent(multipart);
			// 產生mail message
			message.saveChanges();
			Transport transport = mailSession.getTransport("smtp");
			// 傳送
			transport.connect(host, user, pwd);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			logger.info(to + ", send completed.");

		} catch (Exception e) {
			logger.info(this.to + "-" + e.getMessage());
			e.printStackTrace();
		}
	}
}