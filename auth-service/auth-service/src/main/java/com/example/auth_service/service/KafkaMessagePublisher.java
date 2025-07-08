package com.example.auth_service.service;

import com.nimbusds.jose.shaded.gson.Gson;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class KafkaMessagePublisher implements MessagePublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaMessagePublisher(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publishUserRegistration(Map<String, Object> userInfo) {
        String provider = (String) userInfo.get("provider");
        String nickname = null;
        String email = null;

        switch (provider) {
            case "kakao" -> {
                Map<String, Object> kakaoAccount = (Map<String, Object>) userInfo.get("kakao_account");
                Map<String, Object> properties = (Map<String, Object>) userInfo.get("properties");
                nickname = (String) properties.get("nickname");
                email = (String) kakaoAccount.get("email");
            }
            case "google" -> {
                email = (String) userInfo.get("email");
                nickname = (String) userInfo.get("name");
            }
            case "naver" -> {
                Map<String, Object> response = (Map<String, Object>) userInfo.get("response");
                nickname = (String) response.get("nickname");
                email = (String) response.get("email");
            }
            default -> {
                System.out.println("Unknown provider or malformed user info");
                return;
            }
        }

        Map<String, Object> formatted = new HashMap<>();
        formatted.put("provider", provider);
        formatted.put("nickname", nickname);
        formatted.put("email", email);

        System.out.println("User registration info published: " + formatted);
        String json = new Gson().toJson(formatted);
        kafkaTemplate.send("user-registration-topic", json);
    }



}
