package com.iigeo.ssm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 结果封装
 * 
 * @author laowa
 * 
 * @param 
 */
public class BaseBean {
	/* 是否成功*/
	private boolean status;
	/* 错误码*/
    @JsonInclude(Include.NON_NULL)
	private String code;
    /* 错误描述 */
	private String description;
    /* 返回主体 */
	private Object data;
	
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public BaseBean(boolean status, String code, String description, Object data) {
		super();
		this.status = status;
		this.code = code;
		this.description = description;
		this.data = data;
	}

	public BaseBean(boolean status, String description, Object data) {
		super();
		this.status = status;
		this.description = description;
		this.data = data;
	}

	public BaseBean(boolean status, Object data) {
		super();
		this.status = status;
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResultBean [data=" + data + ", description=" + description
				+ ", code=" + code + ", status=" + status + "]";
	}
	
	
}
