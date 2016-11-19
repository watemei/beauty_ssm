package com.iigeo.ssm.util;

public class SystemUtil {
	private PropertiesHandler propertiesHandler = new PropertiesHandler();

	public String getValueByKey(String key) {
		return (String) propertiesHandler.getValueByKey(key);
	}

	public static void main(String[] args) {
		SystemUtil systemUtil = new SystemUtil();
		System.out.println(systemUtil.getValueByKey("redis.pool.maxTotal"));
		Thread thread1 = systemUtil.new MyThread();
		thread1.run();

	}

	class MyThread extends Thread {

		@Override
		public void run() {
			SystemUtil systemUtil = new SystemUtil();
			System.out.println(this.getName() + "线程：" + systemUtil.getValueByKey("redis.pool.maxTotal"));
		}

	}

}
