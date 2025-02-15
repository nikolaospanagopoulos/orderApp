package com.ordering.orderApp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ordering.orderApp.payload.ApiResponse;
import com.ordering.orderApp.payload.JwtResponseDto;
import com.ordering.orderApp.payload.LoginDto;
import com.ordering.orderApp.payload.RegisterDto;
import com.ordering.orderApp.payload.UserDetailsDto;
import com.ordering.orderApp.services.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private AuthService authService;

	public AuthController(AuthService authService) {
		super();
		this.authService = authService;
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
		String response = authService.register(registerDto);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping("/roles")
	public ResponseEntity<String> createRoles() {
		String response = authService.createRoles();
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping("/info")
	public ResponseEntity<ApiResponse<UserDetailsDto>> getUserInfo() {
		UserDetailsDto userDetails = authService.getUserInfo();
		ApiResponse<UserDetailsDto> res = new ApiResponse<>(userDetails);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<JwtResponseDto> login(@RequestBody LoginDto loginDto, HttpServletResponse response) {
		String token = authService.login(loginDto);
		JwtResponseDto jwtResponseDto = new JwtResponseDto();
		jwtResponseDto.setAccessToken(token);
		Cookie jwtCookie = new Cookie("JWT", token);
		jwtCookie.setHttpOnly(true);
		jwtCookie.setPath("/"); // Or specify the path where the cookie is relevant
		jwtCookie.setMaxAge(7 * 24 * 60 * 60); // Expires in 7 days
		response.addCookie(jwtCookie);
		return ResponseEntity.ok(jwtResponseDto);

	}
}
