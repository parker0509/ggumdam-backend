package com.example.auth_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RedisTemplate<String, String> redisTemplate;
    private final long refreshTokenValidityMs = 1000 * 60 * 60 * 24 * 7; // 7일

    public void saveRefreshToken(String email, String refreshToken) {
        redisTemplate.opsForValue().set(email, refreshToken, Duration.ofMillis(refreshTokenValidityMs));
    }

    public String getRefreshToken(String email) {
        return redisTemplate.opsForValue().get(email);
    }

    public void deleteRefreshToken(String email) {
        redisTemplate.delete(email);
    }

    public boolean validate(String email, String token) {
        String savedToken = redisTemplate.opsForValue().get(email);
        return savedToken != null && savedToken.equals(token);
    }
}
