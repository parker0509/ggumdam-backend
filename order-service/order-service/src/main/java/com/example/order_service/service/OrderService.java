package com.example.order_service.service;

import com.example.order_service.domain.Order;
import com.example.order_service.dto.OrderRequest;
import com.example.order_service.dto.OrderResponse;
import com.example.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public OrderResponse CreateOrder(OrderRequest request) {

        Order order = new Order();
        order.setId(request.getUserId());
        order.setProjectid(request.getProjectId());
        order.setQuantity(request.getQuantity());
        order.setTotalAmount(request.getTotalAmount());
        order.setLocalDateTime(LocalDateTime.now());
        order.setUpdateAt(LocalDateTime.now());

        Order saved = orderRepository.save(order);

        return new OrderResponse(saved.getId(), saved.getOrderStatus(), saved.getTotalAmount());
    }

    public OrderResponse getOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return new OrderResponse(order.getId(), order.getOrderStatus(), order.getTotalAmount());
    }
}
