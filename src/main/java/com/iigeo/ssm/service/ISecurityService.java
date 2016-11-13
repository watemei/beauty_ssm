package com.iigeo.ssm.service;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author 安全校验
 *
 */
public interface ISecurityService {
	
	/**
	 * 版本检测
	 * @param request
	 */
	void check(HttpServletRequest request,String version) throws Exception;
	
	/**
	 * 解密
	 * @param request
	 */
	HttpServletRequest decrypt(HttpServletRequest request)throws Exception;

	/**
	 * 授权
	 * @param request
	 */
	void doAuth(HttpServletRequest request)throws Exception;
}
