package com.example.user_service.util;

import com.example.user_service.service.JwtBlacklistService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtUtil {

    @Autowired
    private JwtBlacklistService jwtBlacklistService;

    @Value("${jwt.secret}")
    private String accessSecretKey;  // access token용 시크릿키

    @Value("${jwt.refresh}")
    private String refreshSecretKey;  // refresh token용 시크릿키

    // access token용 SecretKey 변환 메서드
    private SecretKey getAccessSigningKey() {
        return Keys.hmacShaKeyFor(accessSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    // refresh token용 SecretKey 변환 메서드
    private SecretKey getRefreshSigningKey() {
        return Keys.hmacShaKeyFor(refreshSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    // access token에서 이메일 추출
    public String extractEmail(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return claims.getSubject();
        } catch (Exception e) {
            System.out.println("JWT 토큰 파싱 실패: " + e.getMessage());
            throw e;
        }
    }

    // access token 전체 Claims 추출
    private Claims extractAllClaims(String token) throws SignatureException {
        return Jwts.parserBuilder()
                .setSigningKey(getAccessSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // access token 검증
    public boolean isTokenValid(String token) {
        if (jwtBlacklistService.isTokenBlacklisted(token)) {
            System.out.println("token =  블랙리스트 걸림: " + token);
            return false;
        }
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // refresh token 검증
    public boolean validateRefreshToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getRefreshSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
