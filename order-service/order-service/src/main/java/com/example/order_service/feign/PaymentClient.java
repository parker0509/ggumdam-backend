package com.example.order_service.feign;

import com.example.order_service.dto.PaymentRequest;
import com.example.order_service.dto.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", url = "http://localhost:8015") // 실제 포트로 수정
public interface PaymentClient {

    @PostMapping("/api/payments")
    PaymentResponse requestPayment(@RequestBody PaymentRequest paymentRequest);

}
