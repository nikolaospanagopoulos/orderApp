package com.ordering.orderApp.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ordering.orderApp.entities.User;
import com.ordering.orderApp.repositories.UserRepository;

@Service
public class CustomUsersDetailService implements UserDetailsService {

	private UserRepository userRepository;

	public CustomUsersDetailService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		User foundUser = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
				.orElseThrow(() -> new InternalAuthenticationServiceException("Authentication failed"));
		Set<GrantedAuthority> authorities = foundUser.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
		return new org.springframework.security.core.userdetails.User(foundUser.getUsername(), foundUser.getPassword(),
				authorities);
	}

}
