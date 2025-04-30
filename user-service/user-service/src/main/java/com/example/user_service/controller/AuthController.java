package com.example.user_service.controller;


import com.example.user_service.domain.User;
import com.example.user_service.dto.ChangePasswordRequest;
import com.example.user_service.dto.LoginRequest;
import com.example.user_service.dto.RegisterRequest;
import com.example.user_service.dto.UserResponseDto;
import com.example.user_service.service.LoginService;
import com.example.user_service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

/*
REST API의 명확한 구분
보안 및 필터 적용의 범위 설정
버전 관리
정적 리소스와의 충돌 방지
*/
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")

public class AuthController {

    private LoginService loginService;
    private UserService userService;

    @Autowired
    public AuthController(LoginService loginService, UserService userService) {
        this.loginService = loginService;
        this.userService = userService;
    }

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
        userService.createUser(registerRequest);
        return ResponseEntity.ok("회원가입 성공!");
    }

    @Operation(summary = "비밀번호 변경", description = "사용자의 비밀번호를 변경")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비밀번호 변경 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PatchMapping("/users/password")
    public ResponseEntity<?> changePassword(@RequestHeader("Authorization") String authorizationHeader, @RequestBody ChangePasswordRequest request) {
        try {
            String token = authorizationHeader.replace("Bearer ", "");
            userService.changePassword(token, request);
            return ResponseEntity.ok(Map.of("message", "비밀번호가 성공적으로 변경되었습니다."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "비밀번호 변경 중 오류 발생"));
        }
    }
    @Operation(summary = "이메일로 사용자 정보 조회", description = "project-service 등 외부 서비스에서 유저 정보를 조회할 때 사용")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "사용자 없음")
    })

    @GetMapping("/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        Optional<User> userOptional = userService.getUserByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserResponseDto response = UserResponseDto.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .nickname(user.getUsername())
                    .profileImageUrl(user.getProfileImageUrl())
                    .build();

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "해당 이메일의 사용자를 찾을 수 없습니다."));
        }
    }




}
