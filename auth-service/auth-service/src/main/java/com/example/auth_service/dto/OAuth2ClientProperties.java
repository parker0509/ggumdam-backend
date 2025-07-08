package com.example.auth_service.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.security.oauth2.client.registration")
public class OAuth2ClientProperties {
    private Provider google;
    private Provider naver;
    private Provider kakao;

    @Getter
    @Setter
    public static class Provider {
        private String clientId;
        private String redirectUri;
        private String state; // optional (naver)
    }
}
