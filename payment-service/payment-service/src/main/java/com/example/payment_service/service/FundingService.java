/*
package com.example.payment_service.service;

import lombok.RequiredArgsConstructor;
import mall.shopping.mall.entity.*;
import mall.shopping.mall.repository.FundingRepository;
import mall.shopping.mall.repository.FundingSupportRepository;
import mall.shopping.mall.repository.ProjectRepository;
import mall.shopping.mall.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class FundingService {

    @Autowired
    private final FundingRepository fundingRepository;
    private final FundingSupportRepository fundingSupportRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @Transactional
    public void processPayment(Long userId, Long projectId, Long amount, boolean isSuccess) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("찾을 수 없는 프로젝트입니다."));

        FundingSupport fundingSupport = new FundingSupport();
        fundingSupport.setUser(user);
        fundingSupport.setAmount(amount);
        fundingSupport.setPaymentStatus(isSuccess ? PaymentStatus.SUCCESS : PaymentStatus.FAILED);

        fundingSupportRepository.save(fundingSupport);

        if (isSuccess) {
            project.setRaisedAmount(project.getRaisedAmount().add(BigDecimal.valueOf(amount)));// 👈 currentAmount가 아니라 raisedAmount
            projectRepository.save(project);
        }
    }


}
*/
