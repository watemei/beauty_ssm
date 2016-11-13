package com.iigeo.ssm.filter;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSONObject;
import com.iigeo.ssm.service.impl.SecurityService;

public class SecurityAuthFilter implements Filter {
	
	private static Logger LOG = LogManager.getLogger(SecurityAuthFilter.class);
	
	private static final String VERSION = "2.5.1";
	
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest) arg0;
		try {
			getSecurityService(arg0.getServletContext()).check(request, VERSION);
		} catch (Exception e1) {
			chain.doFilter(arg0, arg1);
			return;
		}
		try {
			getSecurityService(arg0.getServletContext()).doAuth(request);
			LOG.info("auth success.");
			chain.doFilter(arg0, arg1);
		} catch (Exception e) {
			LOG.error("auth fail.", e);
			authResponse(arg1);
			return;
		}
	}

	private SecurityService getSecurityService(ServletContext sc){
		return (SecurityService) WebApplicationContextUtils.getWebApplicationContext(sc).getBean("securityService");
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void destroy() {

	}
	
	public void authResponse(ServletResponse arg1){
		try {
			HttpServletResponse response=(HttpServletResponse) arg1;
			JSONObject json=new JSONObject();
			json.put("status", false);
			json.put("code", "0001");
			json.put("description", "auth fail.");
			response.getWriter().println(json.toJSONString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
		}
	}
	
}
