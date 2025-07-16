package com.example.user_service.controller;

import com.example.user_service.domain.User;
import com.example.user_service.dto.ChangePasswordRequest;
import com.example.user_service.dto.LoginVerificationRequest;
import com.example.user_service.dto.RegisterRequest;
import com.example.user_service.dto.UserResponseDto;
import com.example.user_service.service.JwtBlacklistService;
import com.example.user_service.service.UserService;
import com.example.user_service.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

/**
 * 사용자 관련 API를 처리하는 컨트롤러입니다.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtBlacklistService jwtBlacklistService;

    /**
     * 간단한 헬스 체크용 API입니다.
     *
     * @return 확인 메시지
     */
    @GetMapping
    public ResponseEntity<String> testController() {
        return ResponseEntity.status(200).body("확인 되었습니다.");
    }

    /**
     * 이메일로 사용자 정보를 조회합니다.
     * 외부 서비스(project-service 등)에서 사용자 정보를 요청할 때 사용됩니다.
     *
     * @param email 조회할 사용자의 이메일
     * @return 사용자 정보 또는 오류 메시지
     */
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

    /**
     * 일반 회원가입을 처리합니다.
     *
     * @param registerRequest 회원가입 요청 데이터
     * @return 회원가입 성공 메시지
     */
    @Operation(summary = "회원가입", description = "새로운 사용자를 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공")
    })
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest registerRequest) {
        userService.createUser(registerRequest);
        return ResponseEntity.ok("회원가입 성공!");
    }


    @PostMapping("/oauth")
    public ResponseEntity<Void> createOrUpdateFromOAuth(@RequestBody UserResponseDto dto) {
        userService.createOrUpdateFromOAuth(dto);
        System.out.println("dto = " + dto.getEmail()+" oAuth 사용자 입니다.");
        return ResponseEntity.ok().build();
    }

    /**
     * 사용자의 로그인 정보(이메일, 비밀번호)를 검증합니다.
     *
     * @param request 로그인 요청 정보
     * @return 사용자 정보 DTO
     */
    @PostMapping("/verify")
    public UserResponseDto verifyLogin(@RequestBody LoginVerificationRequest request) {
        return userService.verifyUserCredentials(request.getEmail(), request.getPassword());
    }

    /**
     * 현재 로그인한 사용자의 비밀번호를 변경합니다.
     *
     * @param token   인증된 사용자 토큰 (Bearer 포함)
     * @param request 비밀번호 변경 요청 정보
     * @return 변경 완료 메시지
     */
    @PutMapping("/password")
    public ResponseEntity<String> changePassword(
            @RequestHeader("Authorization") String token,
            @RequestBody ChangePasswordRequest request) {

        userService.changePassword(token, request);
        return ResponseEntity.ok("비밀번호 변경 완료");
    }

    /**
     * 사용자 ID로 사용자 정보를 조회합니다.
     *
     * @param id 사용자 ID
     * @return 사용자 정보 OR 오류 메시지
     */
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

    /**
     * JWT 토큰을 기반으로 현재 로그인한 사용자 정보를 조회합니다.
     *
     * @param authHeader Authorization 헤더 (Bearer 포함)
     * @return 사용자 정보 또는 오류 메시지
     */
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String email = jwtUtil.extractEmail(token);

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
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "사용자를 찾을 수 없습니다."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "유효하지 않은 토큰입니다."));
        }
    }

    /**
     * 사용자의 로그아웃을 처리합니다. (토큰 무효화 목적)
     *
     * @param header Authorization 헤더 (Bearer 포함)
     * @return 로그아웃 결과 메시지
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("토큰 누락 또는 형식 오류");
        }

        String token = header.substring(7);
        System.out.println("로그아웃 요청 받은 토큰: " + token);

        // 토큰 무효화 로직 (예: Redis 블랙리스트 등록 등)
        return ResponseEntity.ok("로그아웃 성공");
    }

}
