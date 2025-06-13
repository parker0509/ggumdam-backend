package com.example.auth_service.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setBlacklist(String key, String value, long expiration) {
        redisTemplate.opsForValue().set(key, value, expiration, TimeUnit.MILLISECONDS);
    }

    public boolean isBlacklisted(String key) {
        return redisTemplate.hasKey(key);
    }
}
