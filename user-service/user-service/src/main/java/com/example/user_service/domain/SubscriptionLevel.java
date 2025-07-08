package com.example.user_service.domain;

import lombok.Getter;

@Getter
public enum SubscriptionLevel {
    LEVEL_1("Level 1", "Basic subscription"),
    LEVEL_2("Level 2", "Standard subscription"),
    LEVEL_3("Level 3", "Premium subscription");
    // Level의 차이에 따른 쿠폰 or 할인
    /**
     * level 로 구독 서비스 구분 !🚩
     * TODO: level 쿠폰 발급 필요
     * 1 -> 무료
     * 2 -> 쿠폰 발급
     * 3 -> 배송비 무료
     */
    private final String name;
    private final String description;

    SubscriptionLevel(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
