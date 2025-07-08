package com.example.order_service.feign;

import com.example.order_service.dto.FundingUpdateRequest;
import com.example.order_service.dto.ProjectDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "project-service", url = "http://localhost:8006")  // user-service의 URL
public interface ProjectFeign {


    @GetMapping("/api/free-orders/rewards/{rewardId}")
    ProjectDto getProjectById(@PathVariable("rewardId") Long rewardId);

    @PostMapping("/api/funding-orders/update-after-payment")
    void updateFundingAfterPayment(@RequestBody FundingUpdateRequest request);
}
