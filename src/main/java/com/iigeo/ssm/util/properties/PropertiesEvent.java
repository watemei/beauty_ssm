package com.iigeo.ssm.util.properties;

import org.springframework.context.ApplicationEvent;

public class PropertiesEvent extends ApplicationEvent {
	private static final long serialVersionUID = 1L;

	public PropertiesEvent(PropertiesEventObject source) {
		super(source);
	}

}
