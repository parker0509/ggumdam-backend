package com.example.project_service.fundingTest;

import com.example.project_service.domain.funding.FundingOrder;
import com.example.project_service.domain.reward.Reward;
import com.example.project_service.repository.funding.FundingOrderRepository;
import com.example.project_service.repository.reward.RewardRepository;
import com.example.project_service.service.funding.FundingOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
class FundingOrderServiceTest {

    @Autowired
    private RewardRepository rewardRepository;

    @Autowired
    private FundingOrderService fundingOrderService;

    @Autowired
    private FundingOrderRepository fundingOrderRepository;

    @Test
    void updateToPaymentArchiment() {
        // given
        FundingOrder fundingOrder = new FundingOrder();
        fundingOrder.setGoalAmount(100000); // 목표금액
        fundingOrder.setAmountRaised(20000);
        fundingOrder.setAchievement(20);
        fundingOrder = fundingOrderRepository.save(fundingOrder);

        Reward reward = new Reward();
        reward.setFundingOrder(fundingOrder);
        reward = rewardRepository.save(reward);

        int paymentAmount = 10000;

        // when
        fundingOrderService.updateFundingAfterPayment(reward.getId(), paymentAmount);

        // then
        FundingOrder updated = fundingOrderRepository.findById(fundingOrder.getId()).orElseThrow();
        assertThat(updated.getAmountRaised()).isEqualTo(30000);
        assertThat(updated.getAchievement()).isEqualTo(30);
    }
}
