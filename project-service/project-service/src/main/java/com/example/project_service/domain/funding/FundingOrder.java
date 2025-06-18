package com.example.project_service.domain.funding;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FundingOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long projectId;
    private String rewardName;
    private int quantity;

    private boolean isPaymentCompleted;

    private LocalDateTime createdAt;

    // 펀딩 성공 여부를 위한 상태 필드
    @Enumerated(EnumType.STRING)
    private FundingStatus status;

    public enum FundingStatus {
        WAITING_PAYMENT, // 결제 대기
        SUCCESS_PAYMENT, // 결제 성공
        FAILED_PAYMENT   // 결제 실패 (펀딩 실패 등)
    }

    // getters & setters
}
