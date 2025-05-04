package com.example.auth_service.dto;


public class ChangePasswordRequest {

    private String currentPassword;  // 현재 비밀번호
    private String newPassword;      // 새 비밀번호

    // Getters and Setters
    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
