package com.urbanlife.enums;

public enum RegType {


UL("UL"), FB("FB"), GA(
		"GA");

private final String value;

private RegType(String value) {
	this.value = value;
}

public String getValue() {
	return value;
}
}
