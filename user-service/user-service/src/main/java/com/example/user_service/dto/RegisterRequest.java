package com.example.user_service.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RegisterRequest {

    @NotBlank
    private String username;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    private String phone;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString(){
        return "RegisterRequest{username='" + username + "', email='" + email + "', phone='" + phone + "'}";
    }
}
