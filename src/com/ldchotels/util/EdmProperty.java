package com.ldchotels.util;

public class EdmProperty {
	
	private String mailHost;
	private String mailFrom;
	private boolean mailAuth;
	private String mailUser;
	private String mailPwd;
	private int mailSleep;
	private String mailFromName;
	private int sleepTime = 5000;
	
	private String birthdayEdmList;
	private String birthdayEdmUrl;
	private String birthdayEdmSubject;
		
	public int getSleepTime() {
		return sleepTime;
	}
	
	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}

	public String getMailHost() {
		return mailHost;
	}

	public void setMailHost(String mailHost) {
		this.mailHost = mailHost;
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public boolean isMailAuth() {
		return mailAuth;
	}

	public void setMailAuth(boolean mailAuth) {
		this.mailAuth = mailAuth;
	}

	public String getMailUser() {
		return mailUser;
	}

	public void setMailUser(String mailUser) {
		this.mailUser = mailUser;
	}

	public String getMailPwd() {
		return mailPwd;
	}

	public void setMailPwd(String mailPwd) {
		this.mailPwd = mailPwd;
	}

	public int getMailSleep() {
		return mailSleep;
	}

	public void setMailSleep(int mailSleep) {
		this.mailSleep = mailSleep;
	}

	public String getMailFromName() {
		return mailFromName;
	}

	public void setMailFromName(String mailFromName) {
		this.mailFromName = mailFromName;
	}

	public String getBirthdayEdmList() {
		return birthdayEdmList;
	}

	public void setBirthdayEdmList(String birthdayEdmList) {
		this.birthdayEdmList = birthdayEdmList;
	}

	public String getBirthdayEdmUrl() {
		return birthdayEdmUrl;
	}

	public void setBirthdayEdmUrl(String birthdayEdmUrl) {
		this.birthdayEdmUrl = birthdayEdmUrl;
	}

	public String getBirthdayEdmSubject() {
		return birthdayEdmSubject;
	}

	public void setBirthdayEdmSubject(String birthdayEdmSubject) {
		this.birthdayEdmSubject = birthdayEdmSubject;
	}
}

