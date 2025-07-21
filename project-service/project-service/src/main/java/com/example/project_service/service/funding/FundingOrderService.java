package com.example.project_service.service.funding;

import com.example.project_service.domain.funding.FundingOrder;
import com.example.project_service.domain.reward.Reward;
import com.example.project_service.dto.funding.FundingDto;
import com.example.project_service.repository.funding.FundingOrderRepository;
import com.example.project_service.repository.reward.RewardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 펀딩 주문(FundingOrder)에 대한 비즈니스 로직.
 *
 * - 펀딩 상품 생성
 * - 전체 목록 조회
 * - 좋아요 수 증가
 * - 결제 이후 모금액/달성률 업데이트 등
 */
@Service
@RequiredArgsConstructor
public class FundingOrderService {

    private final FundingOrderRepository fundingOrderRepository;
    private final RewardRepository rewardRepository;

    /**
     * 펀딩 상품을 생성합니다.
     *
     * @param fundingDto 클라이언트로부터 전달받은 펀딩 DTO
     * @return 저장된 FundingOrder 엔티티
     */
    public FundingOrder createOrder(FundingDto fundingDto) {
        FundingOrder fundingOrder = new FundingOrder();

        fundingOrder.setProductName(fundingDto.getProductName());
        fundingOrder.setAchievement(fundingDto.getAchievement());
        fundingOrder.setAmountRaised(fundingDto.getAmountRaised());
        fundingOrder.setParticipants(fundingDto.getParticipants());
        fundingOrder.setQuantity(fundingDto.getQuantity());
        fundingOrder.setCompanyName(fundingDto.getCompanyName());
        fundingOrder.setDaysLeft(fundingDto.getDaysLeft());
        fundingOrder.setSupporters(fundingDto.getSupporters());
        fundingOrder.setShortDescription(fundingDto.getShortDescription());
        fundingOrder.setLikes(0); // 초기 좋아요 수
        fundingOrder.setImageUrl(fundingDto.getImageUrl());
        fundingOrder.setOrderDate(LocalDateTime.now());
        fundingOrder.setGoalAmount(fundingDto.getGoalAmount());

        return fundingOrderRepository.save(fundingOrder);
    }

    /**
     * 전체 펀딩 상품 리스트를 반환.
     *
     * @return 모든 FundingOrder 리스트
     */
    public Page<FundingOrder> getAllOrders(Pageable pageable) {
        return fundingOrderRepository.findAll(pageable);
    }

    /**
     * 특정 펀딩 상품의 좋아요 수를 1 증가.
     *
     * @param id 펀딩 상품 ID
     */
    public void incrementLikes(Long id) {
        FundingOrder fundingOrder = fundingOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not Found"));
        fundingOrder.setLikes(fundingOrder.getLikes() + 1);
        fundingOrderRepository.save(fundingOrder);
    }

    /**
     * ID를 기반으로 특정 펀딩 상품을 조회.
     *
     * @param id 펀딩 상품 ID
     * @return FundingOrder (없으면 null)
     */
    public FundingOrder findById(Long id) {
        return fundingOrderRepository.findById(id).orElse(null);
    }

    /**
     * 결제 완료 후, 해당 리워드가 속한 펀딩 상품의 모금액과 달성률을 업데이트.
     *
     * @param rewardId   결제된 리워드 ID
     * @param paidAmount 결제 금액
     */
    @Transactional
    public void updateFundingAfterPayment(Long rewardId, int paidAmount) {
        Reward reward = rewardRepository.findById(rewardId)
                .orElseThrow(() -> new RuntimeException("Reward not found"));

        FundingOrder funding = reward.getFundingOrder();
        if (funding == null) {
            throw new RuntimeException("연결된 펀딩이 없습니다.");
        }

        int newAmount = funding.getAmountRaised() + paidAmount;
        funding.setAmountRaised(newAmount);

        int achievement = (int) (((double) newAmount / funding.getGoalAmount()) * 100);
        funding.setAchievement(achievement);

        System.out.println("updateFundingAfterPayment 호출됨: rewardId=" + rewardId + ", paidAmount=" + paidAmount);
        System.out.println("achievement = " + achievement);
        fundingOrderRepository.save(funding);
    }
}
