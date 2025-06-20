package com.example.order_service.dto;


import com.example.order_service.domain.OrderStatus;
import lombok.Getter;

@Getter
public class OrderRequest {
    //Order DTO

    private Long userId;
    private Long rewardId;
    private String recipient;
    private String phone;
    private String address;
    private String addressDetail;
    private int totalAmount;
}
