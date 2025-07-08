package com.example.project_service.service.search;

import com.example.project_service.domain.free.FreeOrder;
import com.example.project_service.mapper.FreeOrderMapper;
import com.example.project_service.repository.free.FreeOrderRepository;
import com.example.project_service.repository.search.FreeOrderSearchRepository;
import com.example.project_service.search.FreeOrderDocument;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;



@Service
@AllArgsConstructor
public class FreeOrderIndexSearchService implements CommandLineRunner {

    private final FreeOrderRepository jpaRepository;
    private final FreeOrderSearchRepository elasticRepository;


// kafka로 실시간 동기화 하는 과정 필요 일단 테스트용
    @Override
    public void run(String... args) {
        jpaRepository.findAll().forEach(freeOrder -> {
            FreeOrderDocument document = FreeOrderDocument.builder()
                    .id(freeOrder.getId())
                    .title(freeOrder.getProductName())  // 예시 필드
                    .summary(freeOrder.getShortDescription())
                    .imageUrl(freeOrder.getImageUrl())
                    .build();
            elasticRepository.save(document);
        });
    }
}

