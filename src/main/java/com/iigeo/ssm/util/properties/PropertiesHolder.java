package com.iigeo.ssm.util.properties;

import java.util.HashMap;
import java.util.Map;

public class PropertiesHolder {
	private Map<String,Object> propertiesCache = new HashMap<String,Object>();

	public boolean contains(String key) {
		return propertiesCache.containsKey(key);
	}

	public Object getValueByKey(String key) {
		return propertiesCache.get(key);
	}

	public void setPropertiesCache(Map<String, Object> propertiesCache) {
		this.propertiesCache = propertiesCache;
	}

}
