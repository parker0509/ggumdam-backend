package com.example.order_service.domain;

public enum OrderStatus {
    PENDING,   // 결제 대기
    PAID,      // 결제 완료
    FAILED     // 주문 취소

}
