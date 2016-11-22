package com.iigeo.ssm.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iigeo.ssm.util.properties.PropertiesHandler;

@Component
public class SystemUtil {
	@Autowired
	private PropertiesHandler propertiesHandler;

	public String getValueByKey(String key) {
		return (String) propertiesHandler.getValueByKey(key);
	}

	// public static void main(String[] args) {
	// SystemUtil systemUtil = new SystemUtil();
	// System.out.println(systemUtil.getValueByKey("redis.pool.maxTotal"));
	// Thread thread1 = systemUtil.new MyThread();
	// thread1.run();
	//
	// }
	//
	// class MyThread extends Thread {
	//
	// @Override
	// public void run() {
	// SystemUtil systemUtil = new SystemUtil();
	// System.out.println(this.getName() + "线程：" +
	// systemUtil.getValueByKey("redis.pool.maxTotal"));
	// }
	//
	// }

}
