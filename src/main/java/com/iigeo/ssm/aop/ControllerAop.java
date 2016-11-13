package com.iigeo.ssm.aop;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iigeo.ssm.dto.BaseBean;
import com.iigeo.ssm.util.AESUtil;
import com.iigeo.ssm.util.IpUtil;

/**
 * 
 * @author laowa
 * 
 */
@Aspect
@Component
public class ControllerAop {
	private static Logger log = LogManager.getLogger(ControllerAop.class);
	
	static{  
	    ParserConfig.getGlobalInstance().setAsmEnable(false);  
	    SerializeConfig.getGlobalInstance().setAsmEnable(false);  
	} 

	@Autowired
	private HttpServletRequest request;

	@Pointcut("execution(* com.**.controller..*.*(..))")
	private void anyMethod() {
	}// 定义一个切入点

	/**
	 * 格式： url:%s method:%s usr-key:%s cip:%s clz:%s method:%s reqLen:%s args:%s respLen:%s resp:%s
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("anyMethod()")
	public Object accessLog(ProceedingJoinPoint pjp) throws Throwable {
		String message = getMethodArgs(pjp);
		
		Object result = pjp.proceed();
		String responeStr = JSON.toJSONString(result);
		if (!pjp.getSignature().getName().equals("initBinder")) {
			log.info(String
					.format("url:%s method:%s usr-key:%s cip:%s clz:%s method:%s reqLen:%s args:%s respLen:%s resp:%s",
							request.getServletPath(), request.getMethod(),
							request.getHeader("usr-key"), IpUtil
									.getIpAddr(request), pjp.getTarget()
									.getClass().getName(), pjp.getSignature()
									.getName(), request.getContentLength(),
							message, responeStr.length(), responeStr));
		}
		return result;
	}

	private String getMethodArgs(ProceedingJoinPoint pjp) {
		String message = "(";
		for (Object arg : pjp.getArgs()) {
			try {
				if(null == arg) break;
				if (arg instanceof MultipartFile) {
					break;
				}
				String clzName = arg.getClass().getName();
				if(clzName.contains("ExtendedServletRequestDataBinder") 
						|| clzName.contains("BeanPropertyBindingResult")){
					break;
				}
				PropertyFilter filter=new PropertyFilter() {
					@Override
					public boolean apply(Object object, String name, Object value) {
						if (value !=null && value instanceof MultipartFile) {
							return false;
						}
						return true;
					}
				};
				message += JSON.toJSONString(arg,new SerializeFilter[]{filter},SerializerFeature.IgnoreNonFieldGetter) + ",";
			} catch (Exception e) {
				log.error("请求参数解析失败：", e);
			}
		}
		message = message.substring(0, message.length() - 1) + ")";
		return message;
	}

}