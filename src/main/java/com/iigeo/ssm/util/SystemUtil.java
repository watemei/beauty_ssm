package com.iigeo.ssm.util;

public class SystemUtil {
	private PropertiesHandler propertiesHandler = new PropertiesHandler();
	
	public String getValueByKey(String key) {
		propertiesHandler.getValueByKey(key);
		return "";
	}
	
}
