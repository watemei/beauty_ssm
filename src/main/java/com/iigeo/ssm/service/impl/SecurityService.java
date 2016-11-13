package com.iigeo.ssm.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.iigeo.ssm.service.ISecurityService;
import com.iigeo.ssm.util.security.ParameterRequestWrapper;
import com.iigeo.ssm.util.security.PasswordCallback;
import com.iigeo.ssm.util.security.SimplePasswordCallback;

@Service("securityService")
public class SecurityService implements ISecurityService{

	private static final String DECRYPT_KEY="sifude_param";
	private static final String DECRYPT_SALT="@@__OOO__NO1__##";
	
	private static final String AUTH_KEY="sifude_sign";
	
	@Override
	public void check(HttpServletRequest request,String version) throws Exception{
		String path=request.getServletPath();
		String v=path.substring(path.lastIndexOf("/v")+2, path.length());
		if (v==null || v.trim().length()<=0) {
			throw new Exception("the url is illegal.");
		}
		if (clac(v) < clac(version)) {
			throw new Exception("the version is illegal.");
		}
	}
	
	private static Integer clac(String version){
		Map<Integer, Integer> map=new HashMap<Integer,Integer>();
		map.put(0, 100);
		map.put(1, 10);
		map.put(2, 1);
		
		Integer total=0;
		String[] array=version.split("\\.");
		if (array!=null && array.length>0) {
			for (int i = 0; i < array.length; i++) {
				total+=map.get(i)*Integer.parseInt(array[i]);
			}
		}
		return total;
	}
	
	@Override
	public HttpServletRequest decrypt(HttpServletRequest r) throws Exception {
		Map<String, Object> parameters =parameters(r);
		ParameterRequestWrapper request = new ParameterRequestWrapper((HttpServletRequest)r);
		request.addAllParameters(parameters);
		return request;
	}

	@Override
	public void doAuth(HttpServletRequest request) throws Exception{
//		String token="d41d8cd9-d41d-d41d8cd9-e4332ef7";
		String membId=request.getParameter("membId");
		String salt=SimplePasswordCallback.MD5(membId);
		String sign=request.getParameter(AUTH_KEY);
		PasswordCallback callback= new SimplePasswordCallback();
		callback.setSalt(salt);
		callback.setPassword(sign);
		callback.decryptPassword();
	}
	
	private Map<String, Object> parameters(ServletRequest request){
		Map<String, Object> parameters=new HashMap<String,Object>();
		String paramStr=request.getParameter(DECRYPT_KEY);
		if (paramStr==null || paramStr.length()<=0) {
			return parameters;
		}
		PasswordCallback passwordCallback = new SimplePasswordCallback();
		passwordCallback.setSalt(DECRYPT_SALT);
		passwordCallback.setPassword(paramStr);
		String param=passwordCallback.decryptPassword();
		if (param==null || param.length()<=0) {
			return parameters;
		}
		String[] kvs=param.split("&");
		if (kvs!=null && kvs.length>0) {
			for (String str : kvs) {
				String []kv=str.split("=");
				if (kv!=null && kv.length>0) {
					if (kv.length>1) {
						parameters.put(str.split("=")[0], new String[]{str.split("=")[1]});
					}else{
						parameters.put(str.split("=")[0], null);
					}
				}
			}
		}
		return parameters;
	}
	
	public static void main(String[] args) {
		System.out.println("a=b&".split("&").length);
		System.out.println("a".split("=").length);
		System.out.println("a=".split("=").length);
		Map<Integer, Integer> map=new HashMap<Integer,Integer>();
		map.put(null, null);
		map.put(1, null);
		System.out.println(map.size());}

	public static ISecurityService getSecurityService(ServletContext context) {
		return (SecurityService) WebApplicationContextUtils.getWebApplicationContext(context).getBean("securityService");
	}
}
