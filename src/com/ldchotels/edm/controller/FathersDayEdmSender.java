package com.ldchotels.edm.controller;

public class FathersDayEdmSender extends EdmSender {
	public FathersDayEdmSender(String subject, String edmUrl, String edmList, 
			boolean isReadFile, boolean isReadDB, boolean isActive, int sleepMillisecond) throws Exception {
		super(subject, edmUrl, edmList, isReadFile, isReadDB, isActive, sleepMillisecond);
	}
}