package com.example.order_service.dto;


import com.example.order_service.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class OrderResponse {

    private Long id;
    private OrderStatus orderStatus;
    private int totalAmount;
}

