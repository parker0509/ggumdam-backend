package com.example.payment_service.feign;


import com.example.payment_service.dto.RewardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "project-service", url = "http://localhost:8006")
public interface ProjectClient {


    @GetMapping("/api/free-orders/rewards/{rewardId}")
    RewardDto getProjectById(@PathVariable("rewardId") Long rewardId);

    @GetMapping("/api/funding-orders/rewards/{rewardId}")
    RewardDto getFundingProjectById(@PathVariable(name = "rewardId")Long rewardId);

}
