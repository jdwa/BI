package com.ldchotels.util;

public class PropertyBean {

	private String authorization;
	private String userName;
	private String password;
	private String fileDir;
	private String accountResultFile;
	private String reservationResultFile;
	private String reservationCOResultFile;
	private String transactionResultFile;
	
	private int maxBytesPerBatch = 10000000; // 10 million bytes per batch
	private int maxRowsPerBatch = 5000; // 10 thousand rows per batch
	private int sleepTime = 5000;
	
	private String mailHost;
	private String mailFrom;
	private boolean mailAuth;
	private String mailUser;
	private String mailPwd;
	private int mailSleep;
	private String mailFromName;
	
	private String edmList;
	private String edmUrl;
	private String edmSubject;
	
	private boolean fileDelete = true;
	
	public String getAuthorization() {
		return authorization;
	}
	
	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFileDir() {
		return fileDir;
	}
	
	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}
	
	public int getMaxBytesPerBatch() {
		return maxBytesPerBatch;
	}
	
	public void setMaxBytesPerBatch(int maxBytesPerBatch) {
		this.maxBytesPerBatch = maxBytesPerBatch;
	}
	
	public int getMaxRowsPerBatch() {
		return maxRowsPerBatch;
	}
	
	public void setMaxRowsPerBatch(int maxRowsPerBatch) {
		this.maxRowsPerBatch = maxRowsPerBatch;
	}
	
	public String getAccountResultFile() {
		return accountResultFile;
	}
	
	public void setAccountResultFile(String accountResultFile) {
		this.accountResultFile = accountResultFile;
	}
	
	public String getReservationResultFile() {
		return reservationResultFile;
	}
	
	public void setReservationResultFile(String reservationResultFile) {
		this.reservationResultFile = reservationResultFile;
	}

	public String getReservationCOResultFile() {
		return reservationCOResultFile;
	}

	public void setReservationCOResultFile(String reservationCOResultFile) {
		this.reservationCOResultFile = reservationCOResultFile;
	}
	
	public String getTransactionResultFile() {
		return transactionResultFile;
	}
	
	public void setTransactionResultFile(String transactionResultFile) {
		this.transactionResultFile = transactionResultFile;
	}
	
	public boolean isFileDelete() {
		return fileDelete;
	}
	
	public void setFileDelete(boolean fileDelete) {
		this.fileDelete = fileDelete;
	}
	
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

	public String getEdmList() {
		return edmList;
	}

	public void setEdmList(String edmList) {
		this.edmList = edmList;
	}

	public String getEdmUrl() {
		return edmUrl;
	}

	public void setEdmUrl(String edmUrl) {
		this.edmUrl = edmUrl;
	}

	public String getEdmSubject() {
		return edmSubject;
	}

	public void setEdmSubject(String edmSubject) {
		this.edmSubject = edmSubject;
	}
}

