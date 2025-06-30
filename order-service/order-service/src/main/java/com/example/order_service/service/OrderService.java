package com.example.order_service.service;

import com.example.order_service.domain.Order;
import com.example.order_service.domain.OrderStatus;
import com.example.order_service.dto.*;
import com.example.order_service.feign.UserClient;
import com.example.order_service.feign.PaymentClient;
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
    private PaymentClient paymentClient;

    @Transactional
    public OrderResponse CreateOrder(OrderRequest request) {
        // 1. 사용자 검증
        UserResponse user = userClient.getUserById(request.getUserId());
        if (user == null) {
            throw new RuntimeException("존재하지 않는 사용자입니다.");
        }

        // 2. 주문 생성
        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setRewardId(request.getRewardId());
        order.setRecipient(request.getRecipient());
        order.setPhone(request.getPhone());
        order.setAddress(request.getAddress());
        order.setAddressDetail(request.getAddressDetail());
        order.setTotalAmount(request.getTotalAmount());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setLocalDateTime(LocalDateTime.now());
        order.setUpdateAt(LocalDateTime.now());

        Order saved = orderRepository.save(order);

        // 3. 결제 요청
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setUserId(saved.getUserId());
        paymentRequest.setOrderId(saved.getId());
        paymentRequest.setRewardId(saved.getRewardId());
        paymentRequest.setAmount((int) saved.getTotalAmount());

        System.out.println("입력된 상태 값: " + saved.getOrderStatus());
        saved.setUpdateAt(LocalDateTime.now());
        orderRepository.save(saved);

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
