package com.iigeo.ssm.util;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesWatcher {
	protected static Logger LOG = LoggerFactory.getLogger(PropertiesWatcher.class);
	private Map<String,Object> propertiesCache = null;
	private LoadProperties loadProperties = null;
	FileAlterationMonitor monitor = null;
	
	public PropertiesWatcher(long interval) {
		@SuppressWarnings("static-access")
		File directory = new File(PropertiesWatcher.class.getClassLoader().getSystemResource("/").toString());
        if(interval < 0l){
        	LOG.error("interval need 0l");
        }
		// 创建一个文件观察器用于处理文件的格式
        FileAlterationObserver observer = new FileAlterationObserver(directory, FileFilterUtils.and(
                FileFilterUtils.fileFileFilter(),FileFilterUtils.suffixFileFilter(".properties")));
        //设置文件变化监听器
        observer.addListener(new MyFileListener());
        this.monitor = new FileAlterationMonitor(interval,observer);
	}
	
	public PropertiesWatcher() {
		@SuppressWarnings("static-access")
		File directory = new File(PropertiesWatcher.class.getClassLoader().getSystemResource("/").toString());
		// 轮询间隔 5 秒
		long interval = TimeUnit.SECONDS.toMillis(5);
		// 创建一个文件观察器用于处理文件的格式
		FileAlterationObserver observer = new FileAlterationObserver(directory, FileFilterUtils.and(
				FileFilterUtils.fileFileFilter(),FileFilterUtils.suffixFileFilter(".properties")));
		//设置文件变化监听器
		observer.addListener(new MyFileListener());
		this.monitor = new FileAlterationMonitor(interval,observer);
	}
	
	public void start(){
		try {
			this.monitor.start();
		} catch (Exception e) {
			LOG.error("monitor start fail.", e);
		}
	}
	
	public void stop(){
		try {
			this.monitor.stop();
		} catch (Exception e) {
			LOG.error("monitor start fail.", e);
		}
	}
	
	final class MyFileListener implements FileAlterationListener{
	    @Override
	    public void onStart(FileAlterationObserver fileAlterationObserver) {
	    	LOG.info("monitor start scan files..");
	    }


	    @Override
	    public void onDirectoryCreate(File file) {
	    	LOG.info(file.getName()+" director created.");
	    }


	    @Override
	    public void onDirectoryChange(File file) {
	    	LOG.info(file.getName()+" director changed.");
	    }


	    @Override
	    public void onDirectoryDelete(File file) {
	    	LOG.info(file.getName()+" director deleted.");
	    }


	    @Override
	    public void onFileCreate(File file) {
	    	LOG.info(file.getName()+" created.");
	    }


	    @Override
	    public void onFileChange(File file) {
	        LOG.info(file.getName()+" changed.");
	        //重新加载
	        
	    }


	    @Override
	    public void onFileDelete(File file) {
	        LOG.info(file.getName()+" deleted.");
	    }


	    @Override
	    public void onStop(FileAlterationObserver fileAlterationObserver) {
	        LOG.info("monitor stop scanning..");
	    }
	}

	public void setPropertiesCache(Map<String, Object> propertiesCache) {
		this.propertiesCache = propertiesCache;
	}

	public void setLoadProperties(LoadProperties loadProperties) {
		this.loadProperties = loadProperties;
	}
	
}
