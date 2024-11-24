package com.ordering.orderApp.payload;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ApiResponse<T> {
	private boolean success;
	// show only if not null
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T data;
	private String message;

	public ApiResponse(boolean success, T data, String message) {
		super();
		this.success = success;
		this.data = data;
		this.message = message;
	}

	public ApiResponse(T data, String message) {
		super();
		this.success = true;
		this.data = data;
		this.message = message;
	}

	public ApiResponse() {
		super();
		this.success = true;
		this.message = "OK";
	}

	public ApiResponse(T data) {
		super();
		this.success = true;
		this.data = data;
		this.message = "OK";
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
