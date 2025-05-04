package com.example.auth_service.feign;

import com.example.auth_service.dto.UserResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", url = "http://localhost:8005")  // user-service의 URL
public interface UserClient {

    @GetMapping("/api/users/email")
    UserResponse getUserByEmail(@RequestParam String email);
}
