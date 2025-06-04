package com.example.order_service.controller;

import com.example.order_service.dto.OrderRequest;
import com.example.order_service.dto.OrderResponse;
import com.example.order_service.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping
    public ResponseEntity<OrderResponse> createOrders(@Valid @RequestBody OrderRequest orderRequest) {

        OrderResponse orderResponse = orderService.CreateOrder(orderRequest);
        System.out.println("orderResponse.getOrderStatus() = " + orderResponse.getOrderStatus());
        return ResponseEntity.ok(orderResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderResponse> getOrders(@PathVariable Long id) {
        OrderResponse orderResponse = orderService.getOrder(id);
        return ResponseEntity.ok(orderResponse);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByUserId(@PathVariable Long userId) {
        List<OrderResponse> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }


/*  front 확인 필요
  @GetMapping("/status/{userId}")
    public ResponseEntity<>*/
}
