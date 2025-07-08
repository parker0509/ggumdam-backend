package com.example.auth_service.service;

import org.springframework.stereotype.Service;

import java.util.Map;

public interface MessagePublisher {
    void publishUserRegistration(Map<String, Object> userInfo);
}
