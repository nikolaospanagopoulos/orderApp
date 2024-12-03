package com.ordering.orderApp.services;

import com.ordering.orderApp.payload.LoginDto;
import com.ordering.orderApp.payload.RegisterDto;

public interface AuthService {
	String login(LoginDto loginDto);

	String register(RegisterDto registerDto);
}
