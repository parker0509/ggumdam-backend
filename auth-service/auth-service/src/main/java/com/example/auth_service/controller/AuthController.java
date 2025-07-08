package com.example.auth_service.controller;

import com.example.auth_service.dto.*;
import com.example.auth_service.feign.UserClient;
import com.example.auth_service.provider.JwtTokenProvider;
import com.example.auth_service.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private final LoginService loginService;
    private final RestTemplate restTemplate;
    private final UserClient userClient;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "로그인", description = "사용자의 로그인 처리 및 JWT 토큰 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "잘못된 자격 증명")
    })

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Map<String, String> tokens = loginService.loginUserService(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok(tokens);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("토큰 누락 또는 형식 오류");
        }

        return userClient.logout(authHeader);  // ✅ 헤더 그대로 넘김
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

    @PutMapping("/user/password")
    public ResponseEntity<?> changePassword(@RequestHeader("Authorization") String authorizationHeader,
                                            @RequestBody ChangePasswordRequest request) {
        String token = authorizationHeader.replace("Bearer ", "");
        return userClient.changePassword("Bearer " + token, request);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        System.out.println("받은 refreshToken 값: " + refreshToken);

        if (refreshToken == null || refreshToken.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("리프레시 토큰이 없습니다.");
        }

        if (!jwtTokenProvider.validateToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 리프레시 토큰입니다.");
        }

        String email = jwtTokenProvider.getEmailFromToken(refreshToken);
        String newAccessToken = jwtTokenProvider.createToken(email);
        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
    }



    /*@Operation(summary = "로그인 검증", description = "사용자 이메일과 비밀번호를 검증하여 로그인 인증 처리")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "잘못된 자격 증명")
    })
    @PostMapping("/verify")
    public ResponseEntity<UserResponse> verifyLogin(@RequestBody LoginVerificationRequest request) {
        try {
            // user-service에서 이메일과 비밀번호로 인증
            UserResponse userResponse = userClient.verifyLogin(request);

            // 인증 성공 시
            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            // 인증 실패 시
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }*/

}
