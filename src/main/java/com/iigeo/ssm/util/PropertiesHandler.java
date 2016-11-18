package com.iigeo.ssm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesHandler {
	protected static Logger LOG = LoggerFactory.getLogger(PropertiesHandler.class);
	
	PropertiesHolder propertiesHolder = new PropertiesHolder();
	PropertiesWatcher propertiesWatcher = new PropertiesWatcher();
	LoadProperties loadProperties = new LoadProperties();
	
	public PropertiesHandler(){
		loadProperties.loadProperties(propertiesHolder, propertiesWatcher);
	}
	
	public Object getValueByKey(String key) {
		if(propertiesHolder.contains(key)){
			return propertiesHolder.getValueByKey(key);
		}else{
			try {
				loadProperties.loadProperties(propertiesHolder, propertiesWatcher);
				return propertiesHolder.getValueByKey(key);
			} catch (Exception e) {
				LOG.error("读取默认路径下的properties文件失败", e);
				return null;
			}
		}
	}
}
