package com.example.project_service.service.free;

import com.example.project_service.domain.free.FreeOrder;
import com.example.project_service.repository.free.FreeOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FreeOrderService {

    private final FreeOrderRepository freeOrderRepository;

    public FreeOrder createOrder(FreeOrder order) {
        return freeOrderRepository.save(order);
    }

    public List<FreeOrder> getAllOrders() {
        return freeOrderRepository.findAll();
    }
}
