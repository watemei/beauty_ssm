package com.iigeo.ssm.bean.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.iigeo.ssm.bean.mock.MockUtils;
import com.iigeo.ssm.dto.UserDto;
import com.iigeo.ssm.entity.User;
import com.iigeo.ssm.mapper.OrikaBeanMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/spring/applicationContext.xml" })
public class BeanTest {
	@Autowired
	OrikaBeanMapper mapper;

	@Test
	public void shouldMapUsertoUserDto() {
		// Given
		User user = MockUtils.mockUser();

		// When
		UserDto userDto = mapper.map(user, UserDto.class);

		// Then
		Assert.assertEquals(userDto.getUserId(), "1");
		// Our UserUserDtoMapper told orika mapper how to populate the fullName
		// attribute.
		Assert.assertEquals(userDto.getFullName(), "Pep Guardiola");
		// Our AddressToStringConverter told orika how to convert an Address
		// object to a String.
		Assert.assertEquals(userDto.getAddress(), "25, Barcelona St.");
	}	
}
