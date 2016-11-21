package com.iigeo.ssm.mapper.custom;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;

import org.springframework.stereotype.Component;

import com.iigeo.ssm.dto.UserDto;
import com.iigeo.ssm.entity.User;

/**
 * Custom mapper exposed as a Spring Bean used to customize the mapping between
 * a User and a UserDto.
 * 
 * @author dlizarra
 */
@Component
public class UserUserDtoMapper extends CustomMapper<User, UserDto> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mapAtoB(User user, UserDto userDto, MappingContext context) {
		userDto.setFullName(user.getUserName() + "(" + user.getScore() + ")");
	}

}
