package com.example.project_service.controller.reward;

import com.example.project_service.domain.free.FreeOrder;
import com.example.project_service.domain.reward.Reward;
import com.example.project_service.dto.reward.RewardDto;
import com.example.project_service.dto.reward.RewardRequestDto;
import com.example.project_service.service.free.FreeOrderService;
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

    @GetMapping("/api/free-orders/{id}/rewards")
    public ResponseEntity<List<RewardDto>> getRewards(@PathVariable(name = "id") Long orderId) {
        List<Reward> rewards = rewardService.getRewardsByOrderId(orderId);
        return ResponseEntity.ok(rewards.stream().map(RewardDto::from).toList());
    }

    @PostMapping("/api/rewards")
    public ResponseEntity<RewardDto> createReward(@RequestBody RewardRequestDto requestDto,
                                                  @RequestParam(name = "freeOrderId") Long freeOrderId) {
        // freeOrderId로 FreeOrder 엔티티 조회
        FreeOrder freeOrder = freeOrderService.findById(freeOrderId);
        if (freeOrder == null) {
            return ResponseEntity.badRequest().build();
        }
        // 리워드 생성
        RewardDto rewardDto = rewardService.createReward(requestDto, freeOrder);
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
}
