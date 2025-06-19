package com.example.payment_service.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PaymentResponse {
    private Long paymentId;
    private String paymentStatus;
    private int paidAmount;




}
