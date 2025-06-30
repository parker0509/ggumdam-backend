package com.example.project_service.controller.funding;


import com.example.project_service.domain.free.FreeOrder;
import com.example.project_service.domain.funding.FundingOrder;
import com.example.project_service.dto.funding.FundingDto;
import com.example.project_service.dto.funding.FundingUpdateRequest;
import com.example.project_service.repository.funding.FundingOrderRepository;
import com.example.project_service.service.funding.FundingOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/funding-orders")
@RequiredArgsConstructor
public class FundingOrderController {

    private final FundingOrderService fundingOrderService;
    private final FundingOrderRepository fundingOrderRepository;


    // 전체 조회
    @GetMapping
    public ResponseEntity<List<FundingOrder>> getAll() {
        return ResponseEntity.ok(fundingOrderService.getAllOrders());
    }

    // 상품 만들기
    @PostMapping
    public ResponseEntity<FundingOrder> create(@RequestBody FundingDto fundingOrderDto) {
        return ResponseEntity.ok(fundingOrderService.createOrder(fundingOrderDto));
    }

    // 상품 다량 저장 { !!추후 삭제 필요!! }
    @PostMapping("/bulk")
    public ResponseEntity<?> saveAll(@RequestBody List<FundingOrder> fundingOrder) {
        fundingOrderRepository.saveAll(fundingOrder);
        return ResponseEntity.ok("저장 완료");
    }

    // {fundingOrder} 개별 상품 조회
    @GetMapping("/{id}")
    public ResponseEntity<FundingOrder> getById(@PathVariable("id") Long id) {
        return fundingOrderRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // {fundingOrder} 상품 좋아요 수
    @PostMapping("/{id}/like")
    public ResponseEntity<Void> like(@PathVariable("id") Long id) {
        fundingOrderService.incrementLikes(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update-after-payment")
    public ResponseEntity<Void> updateFundingAfterPayment(@RequestBody FundingUpdateRequest request) {
        fundingOrderService.updateFundingAfterPayment(request.getRewardId(), request.getPaidAmount());
        return ResponseEntity.ok().build();
    }


}