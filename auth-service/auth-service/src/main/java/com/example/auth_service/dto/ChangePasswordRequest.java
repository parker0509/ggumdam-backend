package com.example.auth_service.dto;


import lombok.Getter;

@Getter
public class ChangePasswordRequest {

    private String email;
    // Getters and Setters
    private String currentPassword;  // 현재 비밀번호
    @Getter
    private String newPassword;      // 새 비밀번호

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
