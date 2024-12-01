package com.ordering.orderApp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
	private UserDetailsService userDetailsService;
//	private 
}
