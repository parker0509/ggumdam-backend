package com.example.order_service.service;

import com.example.order_service.domain.Order;
import com.example.order_service.domain.OrderStatus;
import com.example.order_service.dto.*;
import com.example.order_service.feign.ProjectFeign;
import com.example.order_service.feign.UserClient;
import com.example.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserClient userClient;
    @Autowired
    private ProjectFeign projectClient;

//✅ createOrder - API
    @Transactional

    public OrderResponse CreateOrder(OrderRequest request) {

        UserResponse user = userClient.getUserById(request.getUserId());
        if (user == null) throw new RuntimeException("존재하지 않는 사용자입니다.");

        ProjectDto project = projectClient.getProjectById(request.getProjectId());
        if (project == null) throw new RuntimeException("존재하지 않는 프로젝트입니다.");

        int totalAmount = (int) (project.getPrice() * request.getQuantity());

        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setProjectId(request.getProjectId());
        order.setQuantity(request.getQuantity());
        order.setTotalAmount(totalAmount);
        order.setOrderStatus(OrderStatus.valueOf("PAID"));
        order.setLocalDateTime(LocalDateTime.now());
        order.setUpdateAt(LocalDateTime.now());

        Order saved = orderRepository.save(order);

        // 결제 요청
        PaymentRequest paymentRequest = new PaymentRequest(saved.getId(), (long) totalAmount, request.getUserId());

        return new OrderResponse(saved.getId(), saved.getOrderStatus(), saved.getTotalAmount());
    }


    public OrderResponse getOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return new OrderResponse(order.getId(), order.getOrderStatus(), order.getTotalAmount());
    }

    public List<OrderResponse> getOrdersByUserId(Long userId) {
        List<Order> orderList = orderRepository.findByUserId(userId);
        return orderList.stream()
                .map(order -> new OrderResponse(order.getId(), order.getOrderStatus(), order.getTotalAmount()))
                .toList();
    }
}
