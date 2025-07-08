package com.example.auth_service.controller;

import com.example.auth_service.dto.UserRegisterDto;
import com.example.auth_service.dto.UserResponse;
import com.example.auth_service.feign.UserClient;
import com.example.auth_service.provider.JwtTokenProvider;
import com.example.auth_service.service.MessagePublisher;
import com.example.auth_service.service.OAuth2Service;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class OAuth2Controller {

    private final OAuth2Service oAuth2Service;
    private final JwtTokenProvider jwtTokenProvider;
    private final MessagePublisher messagePublisher;
    private final UserClient userClient;

    // 1. OAuth2 인증 요청 -> OAuth 공급자 로그인 페이지로 리다이렉트
    @Operation(summary = "OAuth2 인증 시작 (Authorization 요청)")
    @GetMapping("/authorize/{provider}")
    public ResponseEntity<Void> oauth2Authorize(@PathVariable String provider) {
        // 공급자별 인증 URL 생성
        String authorizationUrl = oAuth2Service.getAuthorizationUrl(provider);

        // 인증 URL로 리다이렉트 응답
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(authorizationUrl));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }


        @GetMapping("/callback/{provider}")
        public ResponseEntity<Void> oauth2Callback(
                @PathVariable String provider,
                @RequestParam String code,
                @RequestParam(required = false) String state) {

            // 1. 사용자 정보 조회
            Map<String, Object> userInfo = oAuth2Service.getUserInfo(provider, code, state);
            userInfo.put("provider", provider);

            // 2. 이메일, 닉네임 추출
            String email = oAuth2Service.extractEmailFromUserInfo(userInfo, provider);
            String username = oAuth2Service.extractUsernameFromUserInfo(userInfo, provider);

            // 3. 사용자 DB 반영 (동기 처리)
            UserResponse userDto = new UserResponse(email,username);
            userClient.createOrUpdateFromOAuth(userDto);  // ⬅ 핵심

        // 4. 후처리용 이벤트 발행 (비동기)
        messagePublisher.publishUserRegistration(userInfo);  // 예: 쿠폰 발급 등

        // 5. JWT 발급 및 리다이렉트
        String jwt = jwtTokenProvider.createToken(email);
        String redirectUrl = "http://localhost:3000/login?token=" + jwt;

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(redirectUrl));

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }


 /*   // 2. OAuth2 콜백 처리 및 JWT 발급
    @Operation(summary = "OAuth2 콜백 처리 및 JWT 발급")
    @GetMapping("/callback/{provider}")
    public ResponseEntity<Void> oauth2Callback(
            @PathVariable String provider,
            @RequestParam String code,
            @RequestParam(required = false) String state) {

        // OAuth 공급자에서 사용자 정보 조회
        Map<String, Object> userInfo = oAuth2Service.getUserInfo(provider, code, state);
        // 👇 여기가 핵심!
        userInfo.put("provider", provider);

        // 사용자 정보를 user-service에 메시징 (예: Kafka)로 전송
        messagePublisher.publishUserRegistration(userInfo);

        // JWT 생성 (예: 이메일 등 식별자 기반)
        String email = oAuth2Service.extractEmailFromUserInfo(userInfo, provider);
        String jwt = jwtTokenProvider.createToken(email);
        String username = oAuth2Service.extractEmailFromUserInfo(userInfo, provider);

        // JWT를 쿼리 파라미터로 프론트엔드 리다이렉션 (URI는 프론트엔드 주소)
        String redirectUrl = "http://localhost:3000/login?token=" + jwt;


        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(redirectUrl));

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }*/



}
