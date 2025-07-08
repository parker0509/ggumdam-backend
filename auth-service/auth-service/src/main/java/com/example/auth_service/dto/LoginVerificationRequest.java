package com.example.auth_service.dto;

import lombok.Getter;

@Getter
public class LoginVerificationRequest {
    private String email;
    private String password;

    public LoginVerificationRequest() {}

    public LoginVerificationRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
