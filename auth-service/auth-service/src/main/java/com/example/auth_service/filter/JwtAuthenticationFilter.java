/*
package com.example.auth_service.filter;


import com.example.auth_service.provider.JwtTokenProvider;
import com.example.auth_service.util.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    private CustomUserDetailsService customUserDetailsService;

    // 🟡 JWT 인증 처리 필터 (모든 요청마다 한 번씩 실행됨)
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1. 요청 헤더에서 JWT 추출
        String token = resolveToken(request);
        // 2. 토큰 유효성 검사
        if (token != null && jwtTokenProvider.validateToken(token)) {

            // 3. 토큰에서 이메일(사용자 정보) 추출
            String email = jwtTokenProvider.getEmailFromToken(token); // 이메일 기반 인증

            // 4. 사용자 정보 조회 (UserDetails)
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

            // 5. Spring Security의 인증 객체 생성
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            // 6. SecurityContext에 인증 객체 저장 (요청이 인증된 것으로 간주됨)
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    // 요청 헤더에서 "Bearer {token}" 형식의 JWT 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);  // "Bearer " 제외한 실제 토큰 반환
        }
        return null;
    }
}
*/
