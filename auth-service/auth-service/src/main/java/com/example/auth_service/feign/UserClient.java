package com.example.auth_service.feign;

import com.example.auth_service.dto.ChangePasswordRequest;
import com.example.auth_service.dto.LoginVerificationRequest;
import com.example.auth_service.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;

/*
1. 유저 정보를 받아와서 로그인 인증
    ✅ FeignClient로 user-service에서 사용자 이메일 기반 정보 가져오기 (UserClient)

    🔧 사용자가 입력한 이메일/비밀번호 기반 유효성 검사

    사용자 정보가 없는 경우 401 처리

    비밀번호는 해시(BCrypt) 비교

    🛠 로그인 성공 시 JWT 발급 (토큰 생성 로직 분리: JwtTokenProvider)

    ✅ 이메일 정보로 UserDetailsService 구현 (CustomUserDetailsService)

    ❗로그인 실패 횟수 제한 (선택사항)
*/

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

    @PostMapping("/api/user/logout")
    ResponseEntity<String> logout(@RequestHeader("Authorization") String token);
}


