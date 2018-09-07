package com.ldchotels.edm.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.ldchotels.athena.dao.EmployeeDao;
import com.ldchotels.athena.model.Employee;
import com.ldchotels.util.ApplicationContextProvider;

public class BirthdayEdmSender extends EdmSender {

	public BirthdayEdmSender(String subject, String edmUrl, String edmList, 
			boolean isReadFile, boolean isReadDB, boolean isActive, int sleepMillisecond) throws Exception {
		super(subject, edmUrl, edmList, isReadFile, isReadDB, isActive, sleepMillisecond);
	}

	protected ArrayList<String> prepareDBList() {
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
    			if (action != null) action.addActionMessage("********** Procrssing " + key +" ***********");
    			employeeDao = (EmployeeDao) dbMap.get(key);
    			if (employeeDao != null) {
    				List<Employee> employeeList = employeeDao.employedList(now.getTime());
    				logger.info("Currently total employed : [" + employeeList.size() + "]");
    				if (action != null) action.addActionMessage("Currently total employed : [" + employeeList.size() + "]");
    				for(int i = 0 ; i < employeeList.size(); i++){
    					Calendar birthday = Calendar.getInstance();
    					birthday.setTime(employeeList.get(i).getBirth_dat());
    					if ((birthday.get(Calendar.MONTH) == now.get(Calendar.MONTH)) 
    						&& (birthday.get(Calendar.DAY_OF_MONTH) == now.get(Calendar.DAY_OF_MONTH))
    						&& (!list.contains(employeeList.get(i).getMail_addr().trim()))){
    						list.add(employeeList.get(i).getMail_addr().trim());
    						logger.info("Birthday MM-DD : [" + employeeList.get(i).getPers_nam() + "][" + (birthday.get(Calendar.MONTH) + 1) + "-" + birthday.get(Calendar.DAY_OF_MONTH) + "]");
    						if (action != null) action.addActionMessage("Birthday MM-DD : [" + employeeList.get(i).getPers_nam() + "][" + (birthday.get(Calendar.MONTH) + 1) + "-" + birthday.get(Calendar.DAY_OF_MONTH) + "]");
    					}
    				}
    			}
    		} catch(Exception e) {
    			logger.error(e.getMessage());
    			if (action != null) action.addActionError(e.getMessage());
    		}
        }	    
 		return list;
	}	
}
