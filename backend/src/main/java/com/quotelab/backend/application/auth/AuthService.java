package com.quotelab.backend.application.auth;

import com.quotelab.backend.domain.user.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.quotelab.backend.domain.user.UserRepository;
import com.quotelab.backend.infrastructure.security.JwtService;

@Service
public class AuthService {
	private final JwtService jwtService;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
		this.jwtService = jwtService;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public AuthResponse register(RegisterRequest request){
		if (userRepository.existsByEmail(request.getEmail())){
			throw new RuntimeException("Email already exists");
		}

		User user = new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

		userRepository.save(user);
		String token = jwtService.generateToken(user.getEmail());

		AuthResponse response = new AuthResponse();
		response.setToken(token);
		return response;
	}

	public AuthResponse login(LoginRequest request) {
		User user = userRepository.findByEmail(request.getEmail())
			.orElseThrow(() -> new RuntimeException("User not found"));
		
		if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())){
			throw new RuntimeException("Password incorrect");
		}

		String token = jwtService.generateToken(user.getEmail());

		AuthResponse response = new AuthResponse();
		response.setToken(token);
		return response;

	}
}
