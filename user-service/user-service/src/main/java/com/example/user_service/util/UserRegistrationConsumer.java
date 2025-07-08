package com.example.user_service.util;

import com.example.user_service.dto.UserInfoDto;
import com.example.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;


@Component
@RequiredArgsConstructor
public class UserRegistrationConsumer {

    private final UserService userService;
    private final Gson gson = new Gson();



    @KafkaListener(topics = "user-registration-topic", groupId = "user-service-group")
    public void consumeUserRegistration(String message) {
        UserInfoDto dto = new Gson().fromJson(message, UserInfoDto.class);
        System.out.println("[" + dto.getProvider() + "] nickname: " + dto.getNickname() + ", email: " + dto.getEmail());
        System.out.println(" 신규 회원입니다. 쿠폰 발급을 시작 합니다. ");

    }



}
