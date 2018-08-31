package com.ldchotels.edm.mail.thread;

import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;

import org.apache.log4j.Logger;

import com.ldchotels.util.EdmProperty;

public class MailSender extends java.lang.Thread {
	private static Logger logger = Logger.getLogger(MailSender.class.getName());


	// 郵件標題
	private String subject;
	// 寄件對向
	private String to;
	// 內容
	private String content;
	// 設定檔
	private EdmProperty edmProperty;

	// 建構
	public MailSender(String to, String subject, String content, EdmProperty edmProperty) {
		super();
		this.to = to;
		this.subject = subject;
		this.content = content;
		this.edmProperty = edmProperty;
	}

	// thread run
	public void run() {
		try {

			// 設定傳送基本資訊
			// 取得mail host名稱
			String host = edmProperty.getMailHost();
			// 取得寄信者mail
			String from = edmProperty.getMailFrom();

			// 取得寄信者帳號
			String user = edmProperty.getMailUser();
			// 取得寄信者帳號密碼
			String pwd = edmProperty.getMailPwd();

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
			message.setFrom(new InternetAddress(from, edmProperty.getMailFromName(), "utf-8"));
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