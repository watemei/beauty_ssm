package com.iigeo.ssm.service;

import java.util.List;

import com.iigeo.ssm.dto.UserDto;
import com.iigeo.ssm.entity.User;

public interface UserService {

	List<User> getUserList(int offset, int limit);

	UserDto getUserDetail();
	 
}
