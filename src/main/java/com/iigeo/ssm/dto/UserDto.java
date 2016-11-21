package com.iigeo.ssm.dto;

import com.iigeo.ssm.entity.User;

public class UserDto extends User{

	private String fullName;
	private String address;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
