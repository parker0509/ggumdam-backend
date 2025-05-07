package com.example.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {
    private Long id;
    private String email;
    private String username;
    private String profileImageUrl;

    // 생성자를 email, nickname으로만 받는 경우 추가
    public UserResponseDto(String email, String username) {
        this.email = email;
        this.username = username;
    }
}
