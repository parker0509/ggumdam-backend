package com.example.auth_service.util;

import com.example.auth_service.dto.UserResponse;
import com.example.auth_service.feign.UserClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserClient userClient;

    public CustomUserDetailsService(UserClient userClient) {
        this.userClient = userClient;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            // Feign Client를 통해 user-service에서 유저 정보 가져오기
            UserResponse userResponse = userClient.getUserByEmail(email);
            return new CustomUserDetails(userResponse);  // CustomUserDetails로 변환하여 반환
        } catch (Exception e) {
            throw new UsernameNotFoundException("유저를 찾을 수 없습니다: " + email);
        }
    }
}
