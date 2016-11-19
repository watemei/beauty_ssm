package com.iigeo.ssm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesHandler {
	protected static Logger LOG = LoggerFactory.getLogger(PropertiesHandler.class);

	PropertiesHolder propertiesHolder = new PropertiesHolder();
	LoadProperties loadProperties = new LoadProperties();

	public PropertiesHandler() {
		loadProperties.loadProperties(propertiesHolder);
	}

	public Object getValueByKey(String key) {
		return propertiesHolder.getValueByKey(key);
	}
}
