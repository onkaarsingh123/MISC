package com.urbanlife.exception;

import java.io.Serializable;

public class UrbanLifeException extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;

	private String strCode = null;

	public UrbanLifeException() {
		super();
	}

	public UrbanLifeException(String msg) {
		super(msg);
	}

	public UrbanLifeException(String msg, Exception e) {
		super(msg, e);
	}

	public UrbanLifeException(String msg, String code) {
		super(msg);
		strCode = code;
	}

	public String getCode() {
		return strCode;
	}

}