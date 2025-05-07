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
    private String nickname;

    public UserResponse(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }
    // + getter/setter

    // 필요하다면 CustomUserDetails에 맞게 조정
}

