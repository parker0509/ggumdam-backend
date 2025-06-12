package com.example.user_service.controller;

import com.example.user_service.domain.User;
import com.example.user_service.dto.ChangePasswordRequest;
import com.example.user_service.dto.LoginVerificationRequest;
import com.example.user_service.dto.RegisterRequest;
import com.example.user_service.dto.UserResponseDto;
import com.example.user_service.service.JwtBlacklistService;
import com.example.user_service.service.UserService;
import com.example.user_service.util.CustomUserDetails;
import com.example.user_service.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtBlacklistService jwtBlacklistService;

    @GetMapping
    public ResponseEntity<String> testController() {
        return ResponseEntity.status(200).body("확인 되었습니다.");
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
                    .username(user.getUsername())
                    .build();

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "해당 이메일의 사용자를 찾을 수 없습니다."));
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

    @PostMapping("/verify")
    public UserResponseDto verifyLogin(@RequestBody LoginVerificationRequest request) {
        // 이메일과 비밀번호를 받아와서 사용자 인증 처리

        return userService.verifyUserCredentials(request.getEmail(), request.getPassword());
    }

    @PutMapping("/password")
    public ResponseEntity<String> changePassword(
            @RequestHeader("Authorization") String token,
            @RequestBody ChangePasswordRequest request) {

        // JWT 토큰 검증 로직 (필요 시 필터 또는 Interceptor로 분리)
        // 비밀번호 변경 로직 실행
        // 예시:
        userService.changePassword(token, request);

        return ResponseEntity.ok("비밀번호 변경 완료");
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<User> userOptional = userService.getUserById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserResponseDto response = UserResponseDto.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .build();

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "해당 ID의 사용자를 찾을 수 없습니다."));
        }
    }
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "인증 정보가 없습니다. 다시 로그인 해주세요."));
        }
        User user = userDetails.getUser();
        return ResponseEntity.ok(UserResponseDto.from(user));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("토큰 누락 또는 형식 오류");
        }

        String token = header.substring(7); // "Bearer " 제거
        System.out.println("로그아웃 요청 받은 토큰: " + token);

        // 토큰 유효성 검사 및 처리
        return ResponseEntity.ok("로그아웃 성공");


    }
}


/*
        REST API의 명확한 구분
        보안 및 필터 적용의 범위 설정
        버전 관리
        정적 리소스와의 충돌 방지
*/


/*
## AuthController-Service
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")

public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        try {
            String token = loginService.loginUserService(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok(Map.of("token", token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest registerRequest) {
        userService.createUser(registerRequest);
        return ResponseEntity.ok("회원가입 성공!");
    }
*/


