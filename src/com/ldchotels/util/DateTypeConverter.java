package com.ldchotels.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.struts2.util.StrutsTypeConverter;

public class DateTypeConverter extends StrutsTypeConverter {
	private static Logger logger = Logger.getLogger(DateTypeConverter.class.getName());

	@Override
	@SuppressWarnings("rawtypes")
    public Object convertFromString(Map context, String[] values, Class toClass) {

        if (values.length > 0 && values[0] != null && values[0].trim().length() > 0) {
            if (values[0].trim().length() == 4) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
	            try {
	                return sdf.parse(values[0]);
	            } catch (ParseException e) {
	            	logger.error("Error converting value ["+values[0]+"] to Date ", e);
	            }
            } else if (values[0].trim().length() == 10) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	            try {
	                return sdf.parse(values[0]);
	            } catch (ParseException e) {
	            	logger.error("Error converting value ["+values[0]+"] to Date ", e);
	            }            	
	        } else {
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	            try {
	                return sdf.parse(values[0]);
	            } catch (ParseException e) {
	            	logger.error("Error converting value ["+values[0]+"] to Date ", e);
	            }            	
	        }
        }
        return null;
    }

	@Override
	@SuppressWarnings("rawtypes")
    public String convertToString(Map context, Object o) {

        if (o instanceof Date) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            return sdf.format((Date) o);
        }
        return "";
    }
}

