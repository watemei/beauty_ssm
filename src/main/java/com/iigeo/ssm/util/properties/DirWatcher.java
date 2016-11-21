package com.iigeo.ssm.util.properties;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DirWatcher implements Runnable {
	protected static Logger LOG = LoggerFactory.getLogger(LoadProperties.class);
	private String fileDirectory;
	private LoadProperties loadProperties;

	public DirWatcher(String fileDirectory, LoadProperties loadProperties) {
		this.fileDirectory = fileDirectory;
		this.loadProperties = loadProperties;
	}

	public void run() {
		WatchService watchService = null;
		try {
			// 获取当前文件系统的WatchService监控对象
			watchService = FileSystems.getDefault().newWatchService();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			// 获取文件目录下的Path对象注册到 watchService中。
			// 监听的事件类型，有创建，删除，以及修改
			Paths.get(fileDirectory).register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (true) {
			WatchKey key = null;
			try {
				// 获取可用key.没有可用的就wait
				key = watchService.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (WatchEvent<?> event : key.pollEvents()) {
				@SuppressWarnings("unchecked")
				WatchEvent<Path> e = (WatchEvent<Path>)event;  
				Path path = e.context(); 
				loadProperties.loadPropertiesFileChange(path.toFile());
				LOG.info(event.context() + "文件:" + event.kind() + "次数: " + event.count());
			}
			// 重置，这一步很重要，否则当前的key就不再会获取将来发生的事件
			boolean valid = key.reset();
			// 失效状态，退出监听
			if (!valid) {
				break;
			}
		}
	}
}
