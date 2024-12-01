package com.ordering.orderApp.security;

import java.io.IOException;
import java.time.Instant;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private static class ErrorResponse {
		private final int status;
		private final String error;
		private final String message;
		private final Instant timestamp;

		public ErrorResponse(int status, String error, String message, Instant timestamp) {
			super();
			this.status = status;
			this.error = error;
			this.message = message;
			this.timestamp = timestamp;
		}

		public int getStatus() {
			return status;
		}

		public String getError() {
			return error;
		}

		public String getMessage() {
			return message;
		}

		public Instant getTimestamp() {
			return timestamp;
		}

	}

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		ErrorResponse errorResponse = new ErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized",
				authException.getMessage(), Instant.now());
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");
		// write error response to output stream
		response.getOutputStream().write(objectMapper.writeValueAsBytes(errorResponse));
	}

}
