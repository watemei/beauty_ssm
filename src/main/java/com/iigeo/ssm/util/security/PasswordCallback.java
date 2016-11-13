package com.iigeo.ssm.util.security;

public interface PasswordCallback {

	void setPassword(String password);

	void setSalt(String password);

	String encryptPassword();

	String decryptPassword();

}
