package com.example.payment_service.service;

import com.example.payment_service.dto.PaymentRequest;
import com.example.payment_service.dto.PaymentResponse;
import com.example.payment_service.dto.RewardDto;
import com.example.payment_service.dto.UserResponse;
import com.example.payment_service.entity.PaymentRecord;
import com.example.payment_service.feign.ProjectClient;
import com.example.payment_service.feign.UserClient;
import com.example.payment_service.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ProjectClient projectClient;
    private final UserClient userClient;

    public PaymentService(PaymentRepository paymentRepository, ProjectClient projectClient, UserClient userClient) {
        this.paymentRepository = paymentRepository;
        this.projectClient = projectClient;
        this.userClient = userClient;
    }

    @Transactional
    public PaymentResponse processPayment(PaymentRequest request) {


        // ✅ 사용자 정보 조회
        UserResponse user = userClient.getUserById(request.getUserId());
        if (user == null) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
        }

        // ✅ 리워드 정보 조회
        RewardDto reward = projectClient.getFundingProjectById(request.getRewardId());
        System.out.println("reward = " + reward);
        System.out.println("PaymentRequest.rewardId = " + request.getRewardId());
        if (reward == null) {
            throw new IllegalArgumentException("유효하지 않은 리워드 ID입니다.");
        }
        // ✅ 결제 금액 검증
/*        int expectedAmount = reward.getPrice() + reward.getShippingFee();
        if (request.getAmount() != expectedAmount) {
            throw new IllegalArgumentException("결제 금액이 올바르지 않습니다.");
        }*/

        // ✅ 결제 저장
        PaymentRecord payment = PaymentRecord.builder()
                .userId(request.getUserId())
                .orderId(request.getOrderId())
                .amount(request.getAmount())
                .rewardId(request.getRewardId()) // ✅ 반드시 있어야 함
                .status("PAID")
                .paidAt(LocalDateTime.now())
                .build();


        PaymentRecord saved = paymentRepository.save(payment);
        System.out.println("Saved payment ID: " + saved.getId() + " / rewardId: " + saved.getRewardId());

        return new PaymentResponse(saved.getId(), saved.getStatus(), saved.getAmount());
    }
}
