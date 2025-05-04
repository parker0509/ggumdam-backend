package com.example.auth_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private Long id;
    private String email;
    private String password;
    private String role;
    // + getter/setter

    // 필요하다면 CustomUserDetails에 맞게 조정
}

