package com.example.order_service.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentRequest {
    private Long userId;
    private Long orderId;
    private int amount;
    private Long rewardId;

    // getters/setters
}

