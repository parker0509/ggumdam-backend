package com.example.project_service.controller.free;

import com.example.project_service.domain.free.FreeOrder;
import com.example.project_service.dto.free.FreeOrderDto;
import com.example.project_service.repository.free.FreeOrderRepository;
import com.example.project_service.service.free.FreeOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/free-orders")
@RequiredArgsConstructor
public class FreeOrderController {

    private final FreeOrderService freeOrderService;
    private final FreeOrderRepository freeOrderRepository;

    @PostMapping
    public ResponseEntity<FreeOrder> create(@RequestBody FreeOrder order) {
        return ResponseEntity.ok(freeOrderService.createOrder(order));
    }

    @GetMapping
    public ResponseEntity<List<FreeOrder>> getAll() {
        return ResponseEntity.ok(freeOrderService.getAllOrders());
    }


    @PostMapping("/bulk")
    public ResponseEntity<?> saveAll(@RequestBody List<FreeOrder> freeOrders) {
        freeOrderRepository.saveAll(freeOrders);
        return ResponseEntity.ok("저장 완료");
    }

    @GetMapping("/{id}")
    public ResponseEntity<FreeOrder> getById(@PathVariable("id") Long id) {
        return freeOrderRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Void> like(@PathVariable("id") Long id) {
        freeOrderService.incrementLikes(id);
        return ResponseEntity.ok().build();
    }


}
