package com.ldchotels.edm.controller;

public class ChristmasEdmSender extends EdmSender {
	public ChristmasEdmSender(String subject, String edmUrl, String edmList, 
			boolean isReadFile, boolean isReadDB, boolean isActive, int sleepMillisecond) throws Exception {
		super(subject, edmUrl, edmList, isReadFile, isReadDB, isActive, sleepMillisecond);
	}
}
