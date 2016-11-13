package com.iigeo.ssm.dto;

import com.iigeo.ssm.enums.ResultEnum;

public class ResultUtil {
	
	public static BaseBean baseBean(Exception e) {
		return new BaseBean(false, "系统内部异常");
	}
	
	public static BaseBean baseBean(boolean status, String code, String description, Object data) {
		return new BaseBean(status, code, description, data);
	}
	
	public static BaseBean baseBean(boolean status, String description, Object data) {
		return new BaseBean(status, null, description, data);
	}
	
	public static BaseBean baseBean(boolean status, ResultEnum resultEnum) {
		return new BaseBean(status, resultEnum.getState()+"", resultEnum.getMsg(), null);
	}
	
}