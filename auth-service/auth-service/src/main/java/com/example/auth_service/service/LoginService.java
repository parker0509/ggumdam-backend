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

@Service
public class LoginService {

    private final UserClient userClient;  // UserClient를 사용하여 user-service와 통신
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public LoginService(UserClient userClient, BCryptPasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userClient = userClient;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // 로그인 검증 기능
    public String loginUserService(String email, String password) {
        LoginVerificationRequest req = new LoginVerificationRequest(email, password);
        UserResponse user = userClient.verifyLogin(req); // 비밀번호 비교는 user-service에서
        return jwtTokenProvider.createToken(user.getEmail());
    }


}
