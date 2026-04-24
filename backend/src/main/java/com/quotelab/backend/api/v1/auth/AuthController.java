package com.quotelab.backend.api.v1.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Autenticação", description = "Registro, login e renovação de token")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	private final AuthService authService;

	public AuthController(AuthService authService){
		this.authService = authService;
	}

	@Operation(summary = "Cadastrar freelancer (POST /api/v1/auth/register)")
	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest request) {
		return ResponseEntity.status(201).body(authService.register(request));
	}

	@Operation(summary = "Login (POST /api/v1/auth/login)")
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request) {
		return ResponseEntity.ok(authService.login(request));
	}

	@Operation(summary = "Renovar access token (POST /api/v1/auth/refresh)")
	@PostMapping("/refresh")
	public ResponseEntity<AuthResponse> refresh(@RequestBody @Valid RefreshRequest request) {
		return ResponseEntity.ok(authService.refresh(request));
	}
}
