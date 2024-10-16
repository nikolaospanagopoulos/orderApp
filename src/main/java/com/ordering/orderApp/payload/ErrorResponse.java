package com.ordering.orderApp.payload;

public class ErrorResponse {
	private boolean success;
	private String message;
	private int errorCode;

	public ErrorResponse(String message, int errorCode) {
		super();
		this.success = false;
		this.message = message;
		this.errorCode = errorCode;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}
