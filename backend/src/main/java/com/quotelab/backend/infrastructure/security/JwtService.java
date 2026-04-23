package com.quotelab.backend.infrastructure.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

@Service
public class JwtService {
	@Value("${jwt.secret}")
	private String secret;


    @Value("${jwt.expiration}")
    private long expiration;

	private SecretKey getSigningKey() {
		byte[] key = Decoders.BASE64.decode(secret);
		return Keys.hmacShaKeyFor(key);
	}

	public String generateToken(String email) {
		return Jwts.builder()
			.subject(email)
			.issuedAt(new Date())
			.expiration(new Date(System.currentTimeMillis() + expiration))
			.signWith(getSigningKey())
			.compact();
	}
	
	public String extractEmail(String token) {
		return Jwts.parser()
			.verifyWith(getSigningKey())
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.getSubject();
	}

	private boolean isTokenExpired(String token) {
		return Jwts.parser()
			.verifyWith(getSigningKey())
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.getExpiration()
			.before(new Date());
	}

	public boolean isTokenValid(String token, String email) {
		return extractEmail(token).equals(email) && !isTokenExpired(token);
	}

	public String generateRefreshToken() {
		return UUID.randomUUID().toString();
	}
}
