package com.example.auth_service.controller;

import com.example.auth_service.dto.ChangePasswordRequest;
import com.example.auth_service.dto.LoginRequest;
import com.example.auth_service.dto.RegisterRequest;
import com.example.auth_service.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private LoginService loginService;
    private RestTemplate restTemplate;

    @Operation(summary = "로그인", description = "사용자의 로그인 처리 및 JWT 토큰 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "잘못된 자격 증명")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        try {
            String token = loginService.loginUserService(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok(Map.of("token", token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        }
    }

    @Operation(summary = "회원가입", description = "새로운 사용자를 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공")
    })

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest registerRequest) {
        String url = "http://localhost:8005/api/users"; // user-service의 회원가입 API 주소
        restTemplate.postForEntity(url, registerRequest, Void.class);
        return ResponseEntity.ok("회원가입 성공!");
    }

    @Operation(summary = "비밀번호 변경", description = "사용자의 비밀번호를 변경")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비밀번호 변경 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })

    @PatchMapping("/users/password")
    public ResponseEntity<?> changePassword(@RequestHeader("Authorization") String authorizationHeader,
                                            @RequestBody ChangePasswordRequest request) {
        String token = authorizationHeader.replace("Bearer ", "");

        // user-service에 토큰과 요청을 전송
        String url = "http://localhost:8005/api/users/password";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ChangePasswordRequest> httpEntity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PATCH, httpEntity, String.class);

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

}
