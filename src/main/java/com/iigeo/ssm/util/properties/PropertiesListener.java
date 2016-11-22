package com.iigeo.ssm.util.properties;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PropertiesListener {
	@Autowired
	PropertiesHandler propertiesHandler;

	@EventListener
	public void handleDemoEvent(PropertiesEvent event) {
		PropertiesEventObject propertiesEventObject = (PropertiesEventObject) event
				.getSource();
		System.out.println("我监听到了pulisher发布的message为:"
				+ propertiesEventObject.getChangePath().toString());
		File changFile = propertiesEventObject.getChangePath().toFile();
		propertiesHandler.loadPropertiesFileChange(changFile);

	}

}