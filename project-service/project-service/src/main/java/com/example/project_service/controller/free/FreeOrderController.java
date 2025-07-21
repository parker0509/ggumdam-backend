package com.example.project_service.controller.free;

import com.example.project_service.domain.free.FreeOrder;
import com.example.project_service.dto.free.FreeOrderDto;
import com.example.project_service.repository.free.FreeOrderRepository;
import com.example.project_service.service.free.FreeOrderService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/free-orders")
@RequiredArgsConstructor
public class FreeOrderController {

    private final FreeOrderService freeOrderService;
    private final FreeOrderRepository freeOrderRepository;
    private static final Logger logger = LoggerFactory.getLogger(FreeOrderController.class);

    /**
     * 애플리케이션 시작 시 로그 기록 테스트용 메서드입니다.
     */
    @PostConstruct
    public void logTest() {
        logger.info("파일 로그 테스트입니다.");
    }

    /**
     * 단일 자유 주문(FreeOrder)을 생성합니다.
     *
     * @param order 생성할 주문 정보 (RequestBody로 받음)
     * @return 생성된 FreeOrder 객체
     */
    @PostMapping
    public ResponseEntity<FreeOrder> create(@RequestBody FreeOrder order) {
        return ResponseEntity.ok(freeOrderService.createOrder(order));
    }

    /**
     * 모든 자유 주문 목록을 페이지네이션하여 조회합니다.
     *
     * @param pageable 페이지 정보 (page, size, sort 등)
     * @return Page 형태의 FreeOrder 목록
     */
    @GetMapping
    public ResponseEntity<Page<FreeOrder>> getAll(Pageable pageable) {
        return ResponseEntity.ok(freeOrderService.getAllOrders(pageable));
    }

    /**
     * 자유 주문 목록을 일괄 저장합니다.
     *
     * @param freeOrders 저장할 주문 리스트
     * @return 저장 완료 메시지
     */
    @PostMapping("/bulk")
    public ResponseEntity<?> saveAll(@RequestBody List<FreeOrder> freeOrders) {
        freeOrderRepository.saveAll(freeOrders);
        return ResponseEntity.ok("저장 완료");
    }

    /**
     * ID로 특정 자유 주문을 조회합니다.
     *
     * @param id 조회할 주문의 ID
     * @return 해당 주문이 존재하면 FreeOrder, 없으면 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<FreeOrder> getById(@PathVariable("id") Long id) {
        return freeOrderRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 특정 자유 주문의 좋아요 수를 1 증가시킵니다.
     *
     * @param id 좋아요를 증가시킬 주문 ID
     * @return 성공 여부 (204 No Content)
     */
    @PostMapping("/{id}/like")
    public ResponseEntity<Void> like(@PathVariable("id") Long id) {
        freeOrderService.incrementLikes(id);
        return ResponseEntity.ok().build();
    }

}
