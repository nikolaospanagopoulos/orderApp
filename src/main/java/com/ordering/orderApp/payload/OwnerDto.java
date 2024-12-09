package com.ordering.orderApp.payload;

public class OwnerDto {
	private String username;
	private String email;

	public OwnerDto(String username, String email) {
		super();
		this.username = username;
		this.email = email;
	}

	public OwnerDto() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "OwnerDto [username=" + username + ", email=" + email + "]";
	}

}
