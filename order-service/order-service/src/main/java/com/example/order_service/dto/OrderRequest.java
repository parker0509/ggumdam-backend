package com.example.order_service.dto;


import com.example.order_service.domain.OrderStatus;
import lombok.Getter;

@Getter
public class OrderRequest {
    //Order DTO

    private Long userId;
    private Long projectId;
    private int quantity;
    private int totalAmount;
    private OrderStatus orderStatus;
}
