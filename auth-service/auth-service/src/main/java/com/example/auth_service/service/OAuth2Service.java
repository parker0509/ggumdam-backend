package com.example.auth_service.service;

import com.example.auth_service.dto.OAuth2ClientProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2Service {

    private final WebClient.Builder webClientBuilder;
    private final OAuth2ClientProperties properties;

    // --- Kakao ---
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String kakaoClientSecret;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUri;

    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String kakaoTokenUri;

    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String kakaoUserInfoUri;

    // --- Naver ---
    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String naverClientId;

    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String naverClientSecret;

    @Value("${spring.security.oauth2.client.registration.naver.redirect-uri}")
    private String naverRedirectUri;

    @Value("${spring.security.oauth2.client.provider.naver.token-uri}")
    private String naverTokenUri;

    @Value("${spring.security.oauth2.client.provider.naver.user-info-uri}")
    private String naverUserInfoUri;

    // --- Google ---
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleClientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleRedirectUri;

    @Value("${spring.security.oauth2.client.provider.google.token-uri}")
    private String googleTokenUri;

    @Value("${spring.security.oauth2.client.provider.google.user-info-uri}")
    private String googleUserInfoUri;

    public Map<String, Object> getUserInfo(String provider, String code, String state) {
        switch (provider.toLowerCase()) {
            case "kakao":
                return getKakaoUserInfo(code);
            case "naver":
                return getNaverUserInfo(code, state);
            case "google":
                return getGoogleUserInfo(code);
            default:
                throw new IllegalArgumentException("지원하지 않는 OAuth 공급자입니다: " + provider);
        }
    }

    private Map<String, Object> getKakaoUserInfo(String code) {
        // 1) 토큰 요청
        Map<String, Object> tokenResponse = webClientBuilder.build()
                .post()
                .uri(kakaoTokenUri)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .bodyValue("grant_type=authorization_code" +
                        "&client_id=" + kakaoClientId +
                        "&client_secret=" + kakaoClientSecret +
                        "&redirect_uri=" + kakaoRedirectUri +
                        "&code=" + code)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();

        String accessToken = (String) tokenResponse.get("access_token");

        // 2) 사용자 정보 요청
        Map<String, Object> userInfo = webClientBuilder.build()
                .get()
                .uri(kakaoUserInfoUri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();

        return userInfo;
    }

    private Map<String, Object> getNaverUserInfo(String code, String state) {
        // 1) 토큰 요청
        Map<String, Object> tokenResponse = webClientBuilder.build()
                .post()
                .uri(naverTokenUri +
                        "?grant_type=authorization_code" +
                        "&client_id=" + naverClientId +
                        "&client_secret=" + naverClientSecret +
                        "&code=" + code +
                        "&state=" + state)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();

        String accessToken = (String) tokenResponse.get("access_token");

        // 2) 사용자 정보 요청
        Map<String, Object> userInfo = webClientBuilder.build()
                .get()
                .uri(naverUserInfoUri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();

        return userInfo;
    }

    private Map<String, Object> getGoogleUserInfo(String code) {
        // 1) 토큰 요청
        Map<String, Object> tokenResponse = webClientBuilder.build()
                .post()
                .uri(googleTokenUri)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .bodyValue("code=" + code +
                        "&client_id=" + googleClientId +
                        "&client_secret=" + googleClientSecret +
                        "&redirect_uri=" + googleRedirectUri +
                        "&grant_type=authorization_code")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();

        String accessToken = (String) tokenResponse.get("access_token");

        // 2) 사용자 정보 요청
        Map<String, Object> userInfo = webClientBuilder.build()
                .get()
                .uri(googleUserInfoUri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();

        return userInfo;
    }

    public String getAuthorizationUrl(String provider) {
        OAuth2ClientProperties.Provider config;

        switch (provider.toLowerCase()) {
            case "kakao":
                config = properties.getKakao();
                return "https://kauth.kakao.com/oauth/authorize?" +
                        "client_id=" + config.getClientId() +
                        "&redirect_uri=" + config.getRedirectUri() +
                        "&response_type=code";
            case "naver":
                config = properties.getNaver();
                return "https://nid.naver.com/oauth2.0/authorize?" +
                        "response_type=code" +
                        "&client_id=" + config.getClientId() +
                        "&redirect_uri=" + config.getRedirectUri() +
                        "&state=STATE_VALUE";
            case "google":
                config = properties.getGoogle();
                return "https://accounts.google.com/o/oauth2/v2/auth?" +
                        "client_id=" + config.getClientId() +
                        "&redirect_uri=" + config.getRedirectUri() +
                        "&response_type=code" +
                        "&scope=email%20profile%20openid";
            default:
                throw new IllegalArgumentException("지원하지 않는 OAuth 공급자입니다: " + provider);
        }
    }
    public String extractEmailFromUserInfo(Map<String, Object> userInfo, String provider) {
        switch (provider.toLowerCase()) {
            case "kakao":
                Map<String, Object> kakaoAccount = (Map<String, Object>) userInfo.get("kakao_account");
                return (String) kakaoAccount.get("email");
            case "naver":
                Map<String, Object> response = (Map<String, Object>) userInfo.get("response");
                return (String) response.get("email");
            case "google":
                return (String) userInfo.get("email");
            default:
                throw new IllegalArgumentException("지원하지 않는 OAuth 공급자입니다: " + provider);
        }
    }

    public String extractUsernameFromUserInfo(Map<String, Object> userInfo, String provider) {
        switch (provider.toLowerCase()) {
            case "kakao":
                Map<String, Object> kakaoAccount = (Map<String, Object>) userInfo.get("kakao_account");
                Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
                return (String) profile.get("nickname");
            case "naver":
                Map<String, Object> response = (Map<String, Object>) userInfo.get("response");
                return (String) response.get("name");
            case "google":
                return (String) userInfo.get("name");
            default:
                return "Unknown";
        }
    }
}
