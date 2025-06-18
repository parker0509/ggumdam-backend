package com.example.order_service.feign;

import com.example.order_service.dto.ProjectDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "project-service", url = "http://localhost:8006")  // user-service의 URL
public interface ProjectFeign {


    @GetMapping("/api/free-orders/rewards/{rewardId}")
    ProjectDto getProjectById(@PathVariable("rewardId") Long rewardId);

}
