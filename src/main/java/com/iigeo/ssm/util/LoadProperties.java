package com.iigeo.ssm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class LoadProperties {
	protected static Logger LOG = LoggerFactory.getLogger(LoadProperties.class);
	private List<File> propertiesFiles = new ArrayList<File>();
	private Map<String,Object> propertiesCache = new HashMap<String,Object>();
	private ExecutorService cachedThreadPool = Executors.newFixedThreadPool(1);  
	public void loadProperties(PropertiesHolder propertiesHolder){
		//遍历默认class路径下的。properties文件
		String clazPath = String.format("%s/WEB-INF/classes", System.getProperty("web.root"));//Class.class.getClass().getResource("/").getPath();
		getPropertiesFiles(clazPath);
		for(File propertiesFile: propertiesFiles){
			setValueInMapFormFile(propertiesFile); 
		}
		propertiesHolder.setPropertiesCache(propertiesCache); 
		FileWatchTask fileWatchTask = new FileWatchTask(clazPath, this);
		
        cachedThreadPool.execute(fileWatchTask);
		LOG.info("LoadProperties file readed finish.");
	}

	private void setValueInMapFormFile(File propertiesFile) {
		LOG.info("properties file read start. {}", propertiesFile.getName());
		try {
			InputStream in = new FileInputStream(propertiesFile);
			ResourceBundle propertyResourceBundle = new PropertyResourceBundle(in);
			Set<String> keys = propertyResourceBundle.keySet();
			for(String key : keys){
				propertiesCache.put(key, propertyResourceBundle.getObject(key));
				LOG.debug(String.format("properties key value.[%s]", propertyResourceBundle.getObject(key)));
			}
		} catch (Exception e) {
			LOG.error("properties file read fail.", e);
		}
		LOG.info("properties file read end. {}", propertiesFile.getName());
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
