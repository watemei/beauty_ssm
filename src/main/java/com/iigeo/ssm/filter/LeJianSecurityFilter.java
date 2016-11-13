package com.iigeo.ssm.filter;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.iigeo.ssm.service.impl.SecurityService;

public class LeJianSecurityFilter implements Filter {
	
	private static Logger LOG = LogManager.getLogger(LeJianSecurityFilter.class);
	
	private static final String VERSION = "2.5.1";

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest) arg0;
		try {
			SecurityService.getSecurityService(arg0.getServletContext()).check(request, VERSION);
			LOG.info("lejian's request,through the security.");
		} catch (Exception e) {
			LOG.info("lejian's request,not through the security.");
			chain.doFilter(arg0, arg1);
			return;
		}
		try {
			request=SecurityService.getSecurityService(arg0.getServletContext()).decrypt(request);
			LOG.info("lejian's decrypt success.");
			chain.doFilter(request, arg1);
		} catch (Exception e) {
			LOG.error("lejian's decrypt fail.",e);
			securityResponse(arg1);
			return;
		}
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void destroy() {

	}
	
	public void securityResponse(ServletResponse arg1){
		try {
			HttpServletResponse response=(HttpServletResponse) arg1;
			JSONObject json=new JSONObject();
			json.put("status", false);
			json.put("code", "0002");
			json.put("description", "lejian's decrypt fail.");
			response.getWriter().println(json.toJSONString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
		}
	}
}
