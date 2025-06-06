package com.example.project_service.service.reward;

import com.example.project_service.domain.free.FreeOrder;
import com.example.project_service.domain.reward.Reward;
import com.example.project_service.dto.reward.RewardDto;
import com.example.project_service.dto.reward.RewardRequestDto;
import com.example.project_service.repository.reward.RewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RewardService {

    @Autowired
    private RewardRepository rewardRepository;

    public List<Reward> getRewardsByOrderId(Long id) {
        return rewardRepository.findByFreeOrderId(id);
    }

    public RewardDto createReward(RewardRequestDto requestDto, FreeOrder freeOrder) {
        Reward reward = requestDto.toEntity();
        reward.setFreeOrder(freeOrder);  // 연관관계 설정
        Reward saved = rewardRepository.save(reward);
        return RewardDto.from(saved);
    }
}
