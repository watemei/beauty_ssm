package com.iigeo.ssm.bean.mock;

import com.iigeo.ssm.entity.Address;
import com.iigeo.ssm.entity.User;

public final class MockUtils {

	private MockUtils() {
		// Non-instantiable class
	}

	public static User mockUser() {
		User user = new User();
		user.setUserId(1);
		user.setUserName("Pep");
		user.setScore(100);
		Address address = new Address();
		address.setNumber(25);
		address.setStreet("Barcelona St.");

		return user;
	}
}
