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

    // ================================================
    // 리워드 생성 API
    // ================================================

    /**
     * FreeOrder에 대한 리워드 생성
     * @param requestDto 리워드 생성 요청 데이터
     * @param freeOrderId 연관된 FreeOrder ID (쿼리 파라미터)
     * @return 생성된 RewardDto 응답
     */
    @PostMapping("/api/rewards/free")
    public ResponseEntity<RewardDto> createReward(@RequestBody RewardRequestDto requestDto,
                                                  @RequestParam(name = "freeOrderId") Long freeOrderId) {
        // freeOrderId로 FreeOrder 엔티티 조회
        FreeOrder freeOrder = freeOrderService.findById(freeOrderId);
        if (freeOrder == null) {
            return ResponseEntity.badRequest().build();
        }
        // 리워드 생성 후 반환
        RewardDto rewardDto = rewardService.createFreeOrderReward(requestDto, freeOrder);
        return ResponseEntity.ok(rewardDto);
    }

    /**
     * FundingOrder에 대한 리워드 생성
     * @param requestDto 리워드 생성 요청 데이터
     * @param fundingOrderId 연관된 FundingOrder ID (쿼리 파라미터)
     * @return 생성된 RewardDto 응답
     */
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

    // ================================================
    // FreeOrder 관련 리워드 조회 API
    // ================================================

    /**
     * 특정 FreeOrder ID에 연결된 모든 리워드 목록 조회
     * @param orderId FreeOrder ID
     * @return 해당 FreeOrder에 속한 RewardDto 리스트 반환
     */

    @GetMapping("/api/free-orders/{id}/rewards")
    public ResponseEntity<List<RewardDto>> getRewards(@PathVariable(name = "id") Long orderId) {
        List<Reward> rewards = rewardService.getRewardsByOrderId(orderId);
        return ResponseEntity.ok(rewards.stream().map(RewardDto::from).toList());
    }

    /**
     * 특정 Reward ID로 단일 FreeOrder 리워드 상세 조회
     * @param rewardId 리워드 ID
     * @return RewardDto 반환 (없으면 404)
     */
    @GetMapping("/api/free-orders/rewards/{rewardId}")
    public ResponseEntity<RewardDto> getReward(
            @PathVariable(name = "rewardId") Long rewardId) {

        Optional<Reward> reward = rewardService.findById(rewardId);
        System.out.println("rewardId = " + rewardId);
        if (reward.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        RewardDto dto = RewardDto.from(reward.get());
        return ResponseEntity.ok(dto);
    }

    // ================================================
    // FundingOrder 관련 리워드 조회 API
    // ================================================

    /**
     * 특정 FundingOrder ID에 연결된 모든 리워드 목록 조회
     * @param orderId FundingOrder ID
     * @return 해당 FundingOrder에 속한 RewardDto 리스트 반환
     */
    @GetMapping("/api/funding-orders/{id}/rewards")
    public ResponseEntity<List<RewardDto>> getFundingOrdersRewards(@PathVariable(name = "id") Long orderId) {
        List<Reward> rewards = rewardService.getRewardsByFundingId(orderId);
        return ResponseEntity.ok(rewards.stream().map(RewardDto::from).toList());
    }

    /**
     * 특정 Reward ID로 단일 FundingOrder 리워드 상세 조회
     * @param rewardId 리워드 ID
     * @return RewardDto 반환 (없으면 404)
     */
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


