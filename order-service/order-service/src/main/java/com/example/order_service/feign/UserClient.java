package com.example.order_service.feign;

import com.example.order_service.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "user-service", url = "http://localhost:8005")  // user-service의 URL
public interface UserClient {

    @GetMapping("/api/user/users/{id}")
    UserResponse getUserById(@PathVariable("id") Long id);

}
