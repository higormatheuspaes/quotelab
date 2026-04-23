package com.quotelab.backend.api.v1.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quotelab.backend.application.auth.AuthResponse;
import com.quotelab.backend.application.auth.AuthService;
import com.quotelab.backend.application.auth.LoginRequest;
import com.quotelab.backend.application.auth.RefreshRequest;
import com.quotelab.backend.application.auth.RegisterRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	private final AuthService authService; // correto

	public AuthController(AuthService authService){
		this.authService = authService;
	}
	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest request) {
		return ResponseEntity.status(201).body(authService.register(request));
	 }

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request) { 
		return ResponseEntity.ok(authService.login(request));
	}

	@PostMapping("/refresh")
	public ResponseEntity<AuthResponse> refresh(@RequestBody @Valid RefreshRequest request) {
		return ResponseEntity.ok(authService.refresh(request));
	}
}
