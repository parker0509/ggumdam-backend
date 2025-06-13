package com.example.auth_service.provider;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    private final long tokenValidityInMilliseconds = 1000 * 60 * 60 * 24; // 24시간
    private final long refreshTokenValidityInMilliseconds = 1000 * 60 * 60 * 24 * 7; // 7일
    private Key key;

    // ✅ 여기 수정된 부분!!
    @PostConstruct
    protected void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        System.out.println("🔑 JWT Key 초기화 완료");
    }

    public String createToken(String email) {
        Claims claims = Jwts.claims().setSubject(email);
        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenValidityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(String email) {
        Claims claims = Jwts.claims().setSubject(email);
        Date now = new Date();
        Date validity = new Date(now.getTime() + refreshTokenValidityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | ExpiredJwtException |
                 UnsupportedJwtException | IllegalArgumentException e) {
            System.out.println("JWT 검증 실패: " + e.getMessage());
            return false;
        }
    }

    public long getExpiration(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key) // ✅ 여기도 key 객체를 써야 함
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration().getTime() - System.currentTimeMillis();
    }
}
