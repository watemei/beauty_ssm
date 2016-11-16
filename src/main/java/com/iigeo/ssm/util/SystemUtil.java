package com.iigeo.ssm.util;

import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public class SystemUtil {
	private static Logger logger = LogManager.getLogger(SystemUtil.class);

	private static ReloadableResourceBundleMessageSource messageSource;

	public SystemUtil() {
	}

	public static void setMessageSource(ReloadableResourceBundleMessageSource messageSource) {
		SystemUtil.messageSource = messageSource;
		logger.debug(
				"***********************" + messageSource.getMessage("ips.durid", null, null) + "***********************");
	}

	public static String getValue(String key) {
		return messageSource.getMessage(key, null, Locale.getDefault());
	}
}
