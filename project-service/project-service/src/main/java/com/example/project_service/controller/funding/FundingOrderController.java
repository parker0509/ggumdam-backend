package com.example.project_service.controller.funding;


import com.example.project_service.domain.free.FreeOrder;
import com.example.project_service.domain.funding.FundingOrder;
import com.example.project_service.dto.funding.FundingDto;
import com.example.project_service.dto.funding.FundingUpdateRequest;
import com.example.project_service.repository.funding.FundingOrderRepository;
import com.example.project_service.service.funding.FundingOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 펀딩 주문(FundingOrder)에 대한 REST 컨트롤러
 * - 펀딩 생성, 목록 조회, 단건 조회, 좋아요 증가, 결제 이후 상태 업데이트 처리
 */
@RestController
@RequestMapping("/api/funding-orders")
@RequiredArgsConstructor
public class FundingOrderController {

    private final FundingOrderService fundingOrderService;
    private final FundingOrderRepository fundingOrderRepository;

    /**
     * [GET] /api/funding-orders
     * 📌 전체 펀딩 상품 목록 조회
     *
     * @return 모든 FundingOrder 목록
     */
    @GetMapping
    public ResponseEntity<Page<FundingOrder>> getAll(@PageableDefault(size = 10)Pageable pageable) {
        return ResponseEntity.ok(fundingOrderService.getAllOrders(pageable));
    }

    /**
     * [POST] /api/funding-orders
     * 📌 새로운 펀딩 상품 생성
     *
     * @param fundingOrderDto 클라이언트로부터 전달받은 펀딩 상품 정보 DTO
     * @return 생성된 FundingOrder 객체
     */
    @PostMapping
    public ResponseEntity<FundingOrder> create(@RequestBody FundingDto fundingOrderDto) {
        return ResponseEntity.ok(fundingOrderService.createOrder(fundingOrderDto));
    }

    /**
     * [POST] /api/funding-orders/bulk
     * ⚠️ 테스트용: 펀딩 상품 여러 개를 한 번에 저장
     *
     * @param fundingOrder 리스트 형식의 FundingOrder 엔티티들
     * @return 저장 완료 메시지
     */
    @PostMapping("/bulk")
    public ResponseEntity<?> saveAll(@RequestBody List<FundingOrder> fundingOrder) {
        fundingOrderRepository.saveAll(fundingOrder);
        return ResponseEntity.ok("저장 완료");
    }

    /**
     * [GET] /api/funding-orders/{id}
     * 📌 특정 펀딩 상품 상세 조회
     *
     * @param id 조회할 FundingOrder의 ID
     * @return 해당 ID에 해당하는 FundingOrder 객체
     */
    @GetMapping("/{id}")
    public ResponseEntity<FundingOrder> getById(@PathVariable("id") Long id) {
        return fundingOrderRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * [POST] /api/funding-orders/{id}/like
     * 📌 좋아요 수 증가 (1회 클릭 시 +1)
     *
     * @param id 좋아요를 증가시킬 FundingOrder의 ID
     * @return 상태 200 OK (바디 없음)
     */
    @PostMapping("/{id}/like")
    public ResponseEntity<Void> like(@PathVariable("id") Long id) {
        fundingOrderService.incrementLikes(id);
        return ResponseEntity.ok().build();
    }

    /**
     * [POST] /api/funding-orders/update-after-payment
     * 📌 결제 완료 이후 모금액, 달성률 업데이트
     * payment-service에서 호출됨 (MSA 간 내부 통신)
     *
     * @param request 리워드 ID, 결제 금액 포함된 요청 객체
     * @return 상태 200 OK (바디 없음)
     */
    @PostMapping("/update-after-payment")
    public ResponseEntity<Void> updateFundingAfterPayment(@RequestBody FundingUpdateRequest request) {
        fundingOrderService.updateFundingAfterPayment(request.getRewardId(), request.getPaidAmount());
        return ResponseEntity.ok().build();
    }
}
