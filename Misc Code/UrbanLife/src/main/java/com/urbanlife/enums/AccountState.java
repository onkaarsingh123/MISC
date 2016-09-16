package com.urbanlife.enums;

public enum AccountState {

	REQUESTED("cc_validation_requested"), FAILED("cc_validation_failed"), ACTIVE(
			"active"), DISABLED("active");

	private final String value;

	private AccountState(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
