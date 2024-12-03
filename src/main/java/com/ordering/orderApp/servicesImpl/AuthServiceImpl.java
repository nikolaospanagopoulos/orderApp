package com.ordering.orderApp.servicesImpl;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ordering.orderApp.entities.Role;
import com.ordering.orderApp.entities.User;
import com.ordering.orderApp.exceptions.ResourceAlreadyExistsException;
import com.ordering.orderApp.payload.LoginDto;
import com.ordering.orderApp.payload.RegisterDto;
import com.ordering.orderApp.repositories.RoleRepository;
import com.ordering.orderApp.repositories.UserRepository;
import com.ordering.orderApp.security.JwtTokenProvider;
import com.ordering.orderApp.services.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	private ModelMapper modelMapper;
	private JwtTokenProvider jwtTokenProvider;

	public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
			RoleRepository roleRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper,
			JwtTokenProvider jwtTokenProvider) {
		super();
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.modelMapper = modelMapper;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public String login(LoginDto loginDto) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtTokenProvider.generateToken(authentication);
		return token;
	}

	@Override
	public String register(RegisterDto registerDto) {
		if (userRepository.existsByEmail(registerDto.getEmail())) {
			throw new ResourceAlreadyExistsException("User already exists with email " + registerDto.getEmail());
		}
		User newUser = modelMapper.map(registerDto, User.class);
		newUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		System.out.println(newUser);
		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName("ROLE_USER").get();
		roles.add(userRole);
		newUser.setRoles(roles);
		userRepository.save(newUser);
		return "User registered successfully";
	}

}
