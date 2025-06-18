package com.example.auth_service.service;

import com.example.auth_service.dto.ChangePasswordRequest;
import com.example.auth_service.dto.LoginVerificationRequest;
import com.example.auth_service.dto.UserResponse;
import com.example.auth_service.feign.UserClient;
import com.example.auth_service.provider.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class LoginService {

    private final UserClient userClient;  // UserClient를 사용하여 user-service와 통신
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    @Autowired
    public LoginService(UserClient userClient, BCryptPasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, RefreshTokenService refreshTokenService) {
        this.userClient = userClient;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenService = refreshTokenService;
    }

    // 로그인 검증 기능

    public Map<String, String> loginUserService(String email, String password) {
        // (1) 로그인 검증 (생략)

        String accessToken = jwtTokenProvider.createToken(email);
        String refreshToken = jwtTokenProvider.createRefreshToken(email); // 리프레시 토큰 생성

        // 둘 다 Map이나 DTO에 담아 리턴
        return Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        );
    }
}
