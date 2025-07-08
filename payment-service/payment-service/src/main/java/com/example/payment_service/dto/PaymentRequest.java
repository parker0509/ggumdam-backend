package com.example.payment_service.dto;

import lombok.*;


@Data
public class PaymentRequest {
    private Long userId;
    private Long orderId;
    private Long rewardId;
    private int amount;
    private String impUid;

    @Override
    public String toString() {
        return "PaymentRequest{" +
                "userId=" + userId +
                ", orderId=" + orderId +
                ", rewardId=" + rewardId +
                ", amount=" + amount +
                ", impUid=" + impUid +
                '}';
    }
}

