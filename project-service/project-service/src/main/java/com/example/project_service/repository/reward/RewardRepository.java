package com.example.project_service.repository.reward;

import com.example.project_service.domain.reward.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RewardRepository extends JpaRepository<Reward,Long> {

    List<Reward> findByFreeOrderId(Long freeOrderId);

}
