package com.example.auth_service.feign;

import com.example.auth_service.dto.ChangePasswordRequest;
import com.example.auth_service.dto.LoginVerificationRequest;
import com.example.auth_service.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "user-service", url = "http://localhost:8005")  // user-service의 URL
public interface UserClient {

    @GetMapping("/api/user/{email}")
    UserResponse getUserByEmail(@PathVariable("email") String email);

    @PostMapping("/api/user/verify")
    UserResponse verifyLogin(@RequestBody LoginVerificationRequest request);

    @PutMapping("/api/user/password")
    ResponseEntity<String> changePassword(
            @RequestHeader("Authorization") String token,
            @RequestBody ChangePasswordRequest request
    );
    @PostMapping("/api/user/oauth")
    void createOrUpdateFromOAuth(@RequestBody UserResponse dto);

    @PostMapping("/api/user/logout")
    ResponseEntity<String> logout(@RequestHeader("Authorization") String token);




}


