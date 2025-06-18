package com.example.project_service.controller.funding;


import com.example.project_service.domain.funding.FundingOrder;
import com.example.project_service.service.funding.FundingOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funding-orders")
@RequiredArgsConstructor
public class FundingOrderController {

    private final FundingOrderService fundingOrderService;

    @PostMapping
    public ResponseEntity<FundingOrder> create(@RequestBody FundingOrder order) {
        return ResponseEntity.ok(fundingOrderService.createFundingOrder(order));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<FundingOrder>> getByProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(fundingOrderService.getOrdersByProject(projectId));
    }
}