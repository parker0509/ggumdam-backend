package com.example.project_service.controller.reward;

import com.example.project_service.domain.free.FreeOrder;
import com.example.project_service.domain.funding.FundingOrder;
import com.example.project_service.domain.reward.Reward;
import com.example.project_service.dto.reward.RewardDto;
import com.example.project_service.dto.reward.RewardRequestDto;
import com.example.project_service.service.free.FreeOrderService;
import com.example.project_service.service.funding.FundingOrderService;
import com.example.project_service.service.reward.RewardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequiredArgsConstructor
public class RewardController {

    private final RewardService rewardService;
    private final FreeOrderService freeOrderService;
    private final FundingOrderService fundingOrderService;

    @GetMapping("/api/free-orders/{id}/rewards")
    public ResponseEntity<List<RewardDto>> getRewards(@PathVariable(name = "id") Long orderId) {
        List<Reward> rewards = rewardService.getRewardsByOrderId(orderId);
        return ResponseEntity.ok(rewards.stream().map(RewardDto::from).toList());
    }

    @PostMapping("/api/rewards/free")
    public ResponseEntity<RewardDto> createReward(@RequestBody RewardRequestDto requestDto,
                                                  @RequestParam(name = "freeOrderId") Long freeOrderId) {
        // freeOrderId로 FreeOrder 엔티티 조회
        FreeOrder freeOrder = freeOrderService.findById(freeOrderId);
        if (freeOrder == null) {
            return ResponseEntity.badRequest().build();
        }
        // 리워드 생성
        RewardDto rewardDto = rewardService.createFreeOrderReward(requestDto, freeOrder);
        return ResponseEntity.ok(rewardDto);
    }

    @PostMapping("/api/rewards/funding")
    public ResponseEntity<RewardDto> createRewardForFunding(@RequestBody RewardRequestDto requestDto,
                                                            @RequestParam(name = "fundingOrderId") Long fundingOrderId) {
        FundingOrder fundingOrder = fundingOrderService.findById(fundingOrderId);
        if (fundingOrder == null) {
            return ResponseEntity.badRequest().build();
        }
        RewardDto rewardDto = rewardService.createFundingOrderReward(requestDto, fundingOrder);
        return ResponseEntity.ok(rewardDto);
    }

    @GetMapping("/api/free-orders/rewards/{rewardId}")
    public ResponseEntity<RewardDto> getReward(
            @PathVariable(name = "rewardId") Long rewardId) {

        Optional<Reward> reward = rewardService.findById(rewardId);
        if (reward.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        RewardDto dto = RewardDto.from(reward.get());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/api/funding-orders/{id}/rewards")
    public ResponseEntity<List<RewardDto>> getFundingOrdersRewards(@PathVariable(name = "id") Long orderId) {
        List<Reward> rewards = rewardService.getRewardsByFundingId(orderId);
        return ResponseEntity.ok(rewards.stream().map(RewardDto::from).toList());
    }

    @GetMapping("/api/funding-orders/rewards/{rewardId}")
    public ResponseEntity<RewardDto> getFundingOrdersReward(
            @PathVariable(name = "rewardId") Long rewardId) {

        Optional<Reward> reward = rewardService.findById(rewardId);
        if (reward.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        RewardDto dto = RewardDto.from(reward.get());
        return ResponseEntity.ok(dto);
    }
}
