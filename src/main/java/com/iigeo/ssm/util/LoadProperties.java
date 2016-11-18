package com.iigeo.ssm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadProperties {
	protected static Logger LOG = LoggerFactory.getLogger(LoadProperties.class);
	
	private List<File> propertiesFiles = new ArrayList<File>();
	private Map<String,Object> propertiesCache = new HashMap<String,Object>();

	public void loadProperties(PropertiesHolder propertiesHolder, PropertiesWatcher propertiesWatcher){
		//遍历默认class路径下的。properties文件
		String clazPath = Thread.currentThread().getContextClassLoader().getResource("").toString();
		getPropertiesFiles(clazPath);
		for(File propertiesFile: propertiesFiles){
			setValueInMapFormFile(propertiesFile); 
		}
		propertiesHolder.setPropertiesCache(propertiesCache); 
		propertiesWatcher.setPropertiesCache(propertiesCache);
		propertiesWatcher.setLoadProperties(this);
	}

	private void setValueInMapFormFile(File propertiesFile) {
		try {
			InputStream in = new FileInputStream(propertiesFile);
			ResourceBundle propertyResourceBundle = new PropertyResourceBundle(in);
			Set<String> keys = propertyResourceBundle.keySet();
			for(String key : keys){
				propertiesCache.put(key, propertyResourceBundle.getObject(key));
			}
		} catch (Exception e) {
			LOG.error("properties file read fail.", e);
		}
	}

	public void loadPropertiesFileChange(File file){
		setValueInMapFormFile(file);
	}
	
	private void getPropertiesFiles(String clazPath) {
		File root = new File(clazPath);
		File[] files = root.listFiles();
		if(null != files){
			for(File file : files){
				if(file.isDirectory()){
					this.getPropertiesFiles(file.getAbsolutePath());
				}else if(file.getName().contains(".properties")){
					this.propertiesFiles.add(file);
				}
			}
		}
	}

}
