package com.example.project_service.controller.free;

import com.example.project_service.domain.free.FreeOrder;
import com.example.project_service.service.free.FreeOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/free-orders")
@RequiredArgsConstructor
public class FreeOrderController {

    private final FreeOrderService freeOrderService;

    @PostMapping
    public ResponseEntity<FreeOrder> create(@RequestBody FreeOrder order) {
        return ResponseEntity.ok(freeOrderService.createOrder(order));
    }

    @GetMapping
    public ResponseEntity<List<FreeOrder>> getAll() {
        return ResponseEntity.ok(freeOrderService.getAllOrders());
    }
}
