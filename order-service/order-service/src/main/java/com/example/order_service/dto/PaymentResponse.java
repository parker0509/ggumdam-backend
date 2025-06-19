package com.example.order_service.dto;


import lombok.Getter;

@Getter
public class PaymentResponse {
    private Long paymentId;
    private String paymentStatus;
    private int paidAmount;

    // getters/setters
}


