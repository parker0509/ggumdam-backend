package com.example.order_service.feign;

import com.example.order_service.dto.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "payment-service", url = "http://localhost:8015")  // user-service의 URL
public interface PaymentFeign {

    @PostMapping("/api/payments")
    void requestPayment(PaymentRequest paymentRequest);
}
