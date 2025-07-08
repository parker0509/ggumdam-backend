package com.example.auth_service.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String email;
    private String nickname;

    public UserResponse(String email, String username) {
        this.email = email;
        this.nickname = username;
    }
}


