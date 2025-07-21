package com.example.project_service.service.free;

import com.example.project_service.domain.free.FreeOrder;
import com.example.project_service.repository.free.FreeOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FreeOrderService {

    private final FreeOrderRepository freeOrderRepository;

    public FreeOrder createOrder(FreeOrder order) {
        return freeOrderRepository.save(order);
    }

    public Page<FreeOrder> getAllOrders(Pageable pageable) {
        return freeOrderRepository.findAll(pageable);
    }

    public void incrementLikes(Long id){
        FreeOrder order = freeOrderRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
        order.setLikes(order.getLikes() + 1);
        freeOrderRepository.save(order);
    }

    public FreeOrder findById(Long id) {
        return freeOrderRepository.findById(id).orElse(null);
    }
}
