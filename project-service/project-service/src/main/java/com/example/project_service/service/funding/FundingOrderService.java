package com.example.project_service.service.funding;


import com.example.project_service.domain.free.FreeOrder;
import com.example.project_service.domain.funding.FundingOrder;
import com.example.project_service.dto.funding.FundingDto;
import com.example.project_service.repository.funding.FundingOrderRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FundingOrderService {


    private FundingOrderRepository fundingOrderRepository;

    @Autowired
    public FundingOrderService(FundingOrderRepository fundingOrderRepository) {
        this.fundingOrderRepository = fundingOrderRepository;
    }

// 상품 생성 DTO 사용
public FundingOrder createOrder(FundingDto fundingDto) {
    FundingOrder fundingOrder = new FundingOrder();
    fundingOrder.setProductName(fundingDto.getProductName());

    // id는 직접 설정 ❌ (DB가 자동 생성)
    fundingOrder.setAchievement(fundingDto.getAchievement());
    fundingOrder.setAmountRaised(fundingDto.getAmountRaised());
    fundingOrder.setParticipants(fundingDto.getParticipants());  // ✅ 수정
    fundingOrder.setQuantity(fundingDto.getQuantity());          // ✅ 수정
    fundingOrder.setCompanyName(fundingDto.getCompanyName());
    fundingOrder.setDaysLeft(fundingDto.getDaysLeft());
    fundingOrder.setSupporters(fundingDto.getSupporters());      // ✅ 수정
    fundingOrder.setShortDescription(fundingDto.getShortDescription());
    fundingOrder.setLikes(0); // 기본값
    fundingOrder.setImageUrl(fundingDto.getImageUrl());
    fundingOrder.setOrderDate(LocalDateTime.now());

    return fundingOrderRepository.save(fundingOrder);
}


// 상품 전체 조회
    public List<FundingOrder> getAllOrders() {
        return fundingOrderRepository.findAll();
    }

    public void incrementLikes(Long id) {
        FundingOrder fundingOrder = fundingOrderRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
        fundingOrder.setLikes(fundingOrder.getLikes() + 1);
        fundingOrderRepository.save(fundingOrder);
    }

    public FundingOrder findById(Long id) {
        return fundingOrderRepository.findById(id).orElse(null);
    }

    // 임시 예시 메서드
}
