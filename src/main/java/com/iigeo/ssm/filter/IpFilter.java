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

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.iigeo.ssm.util.IpUtil;
import com.iigeo.ssm.util.SystemUtil;


public class IpFilter implements Filter {
	private static Logger log = LogManager.getLogger(IpFilter.class);
	
	@Autowired
	private SystemUtil systemUtil;
	

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String remoteIp = IpUtil.getIpAddr((HttpServletRequest)request);
		String ips = "";
		log.info(String.format("Remote Ip:%s", remoteIp));
		if(StringUtils.isBlank(ips)){
			chain.doFilter(request, response);
		}else{
			String[] iparry = StringUtils.split(ips, ",");
			for(String ip: iparry){
				if(StringUtils.equals(ip, remoteIp)){
					chain.doFilter(request, response);
					return;
				}
			}
			securityResponse(response);
		}
		
	}
	
	public void securityResponse(ServletResponse arg1){
		try {
			HttpServletResponse response=(HttpServletResponse) arg1;
			JSONObject json=new JSONObject();
			json.put("status", false);
			json.put("code", "0003");
			json.put("description", "Remote ip not in whitelist fail.");
			response.getWriter().println(json.toJSONString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
		}
	}

	@Override
	public void destroy() {
	}
	
}
