package com.iigeo.ssm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.iigeo.ssm.util.IpUtil;
import com.iigeo.ssm.util.SystemUtil;


public class IpInterceptor extends HandlerInterceptorAdapter {
	private static Logger log = LogManager.getLogger(IpInterceptor.class);
	
	@Autowired
	private SystemUtil systemUtil;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		boolean status = false;
		String remoteIp = IpUtil.getIpAddr(request);
		log.info(remoteIp);
		String ips = SystemUtil.getValue("ips.durid");
		if(StringUtils.isBlank(ips)){
			return true;
		}
		String[] iparry = StringUtils.split(ips, ",");
		
		for(String ip: iparry){
			if(StringUtils.equals(ip, remoteIp)){
				status = true;
				break;
			}
		}
		
		return status;
	}
	
}
