package com.example.auth_service.dto;

import lombok.Getter;
import lombok.Setter;
// src/main/java/com/example/user_service/dto/UserResponse.java
import lombok.NoArgsConstructor;


@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String email;
    private String role;
    private String nickname;
    private String password;
}


