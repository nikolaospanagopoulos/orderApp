package com.ordering.orderApp.payload;

public class LoginDto {
	private String usernameOrEmail;
	private String password;

	public LoginDto(String email, String password) {
		super();
		this.usernameOrEmail = email;
		this.password = password;
	}

	public String getUsernameOrEmail() {
		return usernameOrEmail;
	}

	public void setUsernameOrEmail(String usernameOrEmail) {
		this.usernameOrEmail = usernameOrEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
