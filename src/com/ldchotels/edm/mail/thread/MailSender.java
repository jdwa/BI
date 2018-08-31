package com.ldchotels.edm.mail.thread;

import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;

import org.apache.log4j.Logger;

import com.ldchotels.util.EdmProperty;

public class MailSender extends java.lang.Thread {
	private static Logger logger = Logger.getLogger(MailSender.class.getName());


	// �l����D
	private String subject;
	// �H���V
	private String to;
	// ���e
	private String content;
	// �]�w��
	private EdmProperty edmProperty;

	// �غc
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

			// �]�w�ǰe�򥻸�T
			// ���omail host�W��
			String host = edmProperty.getMailHost();
			// ���o�H�H��mail
			String from = edmProperty.getMailFrom();

			// ���o�H�H�̱b��
			String user = edmProperty.getMailUser();
			// ���o�H�H�̱b���K�X
			String pwd = edmProperty.getMailPwd();

			Properties props = System.getProperties();

			// �]�wSMTP server

			props.put("mail.smtp.host", host);
			// �O�_�ݭn�{��
			props.put("mail.smtp.auth", "true");

			// �̾�Properties�إߤ@��Session

			Session mailSession = Session.getInstance(props, null);

			// �qSession�إߤ@��Message
			MimeMessage message = new MimeMessage(mailSession);

			// �]�wmail From
			message.setFrom(new InternetAddress(from, edmProperty.getMailFromName(), "utf-8"));
			// �]�wmail To
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			// �]�w���D
			message.setSubject(this.subject, "utf-8");

			// �]�w�H����
			message.setSentDate(new Date());
			// �إ߶l�󥻤夺�e
			Multipart multipart = new MimeMultipart();

			// �l�󤺮e
			BodyPart contentPart = new MimeBodyPart();
			// ��BodyPart�ﹳ�]�m���e�M�榡/�s�X�覡
			contentPart.setContent(content, "text/html;charset=utf8");

			// �[�J�l�󤺮e��mutipart��
			multipart.addBodyPart(contentPart);

			// �Nmultipart�[��mail��message��
			message.setContent(multipart);
			// ����mail message
			message.saveChanges();

			Transport transport = mailSession.getTransport("smtp");
			// �ǰe
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