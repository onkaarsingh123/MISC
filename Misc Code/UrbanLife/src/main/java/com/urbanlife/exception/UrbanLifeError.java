package com.urbanlife.exception;

import java.io.Serializable;

public class UrbanLifeError implements Serializable {
	
	private String errorCode = null;
	private String errorMessage = null;
	private String causeMessage = null;
	private String suppressedMessage = null;
//	private Throwable throwable = null;
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getCauseMessage() {
		return causeMessage;
	}
	public void setCauseMessage(String causeMessage) {
		this.causeMessage = causeMessage;
	}

	public String getSuppressedMessage() {
		return suppressedMessage;
	}
	public void setSuppressedMessage(Throwable[] throwables) {
		if(throwables != null)
		this.suppressedMessage = throwables.toString();
	}


}
