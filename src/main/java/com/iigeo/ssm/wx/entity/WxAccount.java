package com.iigeo.ssm.wx.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.iigeo.ssm.util.CustomDateSerializer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class WxAccount {

	private long wxAccId;

	private int subscribe;

	private String openid;

	private String nickname;
	
	private String language;
	
	private String city;
	
	private String province;
	
	private String country;
	
	private String headimgurl;
	
	private String unionid;
	
	private String remark;
	
	private String groupid;
	
	private String tagidList;
	
	private Date subscribeTime;
	
	private int sex;

	// 这里展示了jackson封装好的以及自定义的对时间格式的转换方式
	// 后续对于一些复杂的转换可以自定义转换方式
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createTime;

	@JsonSerialize(using = CustomDateSerializer.class)
	private Date updateTime;

}
