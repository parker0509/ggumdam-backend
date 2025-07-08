package com.example.user_service.dto;

import lombok.*;
import com.example.user_service.domain.User; // 실제 User 엔티티 import

@Getter
@Setter
@Builder
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String email;
    private String username;

    public UserResponseDto(Long id, String email, String username) {
        this.id = id;
        this.email = email;
        this.username = username;
    }

    public UserResponseDto(String email, String username) {
        this.email = email;
        this.username = username;
    }

    public static UserResponseDto from(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getEmail(),      // 수정된 부분
                user.getUsername()

        );
    }
}
