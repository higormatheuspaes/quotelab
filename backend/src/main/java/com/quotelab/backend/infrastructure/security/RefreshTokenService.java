package com.quotelab.backend.infrastructure.security;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
public class RefreshTokenService {
	private final RedisTemplate<String, String> redisTemplate;

	@Value("${jwt.refresh-expiration}")
	private long refreshExpiration;

	public RefreshTokenService(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void save(String token, String email) {
		redisTemplate.opsForValue().set("refresh_token:" + token, email, refreshExpiration, TimeUnit.MILLISECONDS);
	}

	public String findEmail(String token) {
		return redisTemplate.opsForValue().get("refresh_token:" + token);
	}

	public void delete(String token) {
		redisTemplate.delete("refresh_token:" + token);	
	}

}
