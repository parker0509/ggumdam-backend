package com.example.order_service.dto;


import com.example.order_service.domain.OrderStatus;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderResponse {

    private Long id;
    private OrderStatus orderStatus;
    private int totalAmount;

}

