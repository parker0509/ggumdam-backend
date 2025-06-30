package com.example.order_service.controller;

import com.example.order_service.dto.FundingUpdateRequest;
import com.example.order_service.dto.OrderRequest;
import com.example.order_service.dto.OrderResponse;
import com.example.order_service.feign.ProjectFeign;
import com.example.order_service.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProjectFeign projectFeign;  // Feign Client 주입

    @PostMapping
    public ResponseEntity<OrderResponse> createOrders(@Valid @RequestBody OrderRequest orderRequest) {
        OrderResponse orderResponse = orderService.CreateOrder(orderRequest);

        try {
            FundingUpdateRequest updateRequest = new FundingUpdateRequest();
            updateRequest.setRewardId(orderRequest.getRewardId());
            updateRequest.setPaidAmount(orderRequest.getPaidAmount());  // 결제 금액

            projectFeign.updateFundingAfterPayment(updateRequest);
        } catch (Exception e) {
            System.err.println("project-service 펀딩 업데이트 실패: " + e.getMessage());
        }

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
