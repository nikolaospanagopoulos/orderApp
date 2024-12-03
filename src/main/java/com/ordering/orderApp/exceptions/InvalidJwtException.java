package com.ordering.orderApp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidJwtException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String problem;

	public InvalidJwtException(String problem) {
		super(String.format("Invalid JWT token: %s", problem));
		this.problem = problem;
	}

	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
