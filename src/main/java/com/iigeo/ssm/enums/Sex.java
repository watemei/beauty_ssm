package com.iigeo.ssm.enums;

/**
 * 
 * @author waterw
 *
 */
public enum Sex {

	MAN(1, "男"), FEMALE(0, "女");

	private int value;

	private String msg;

	Sex(int value, String msg) {
		this.value = value;
		this.msg = msg;
	}

	public int getValue() {
		return value;
	}

	public String getMsg() {
		return msg;
	}
}
