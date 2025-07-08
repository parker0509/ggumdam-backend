package com.example.user_service.service;

import com.example.user_service.domain.User;
import com.example.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;


    @Autowired
    public LoginService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // !!로그인에 필요한 기능
    // 로그인 검증 (email 로 검증)

    /*
     * 로그인에 필요한 기능
     * 1. 로그인시 UserRepository와 검증
     * 2. Session 과 JWT 구분
     * 3. 추후 생각 날시 적기
     *
     * */



}
