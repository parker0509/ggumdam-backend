package com.example.project_service.controller.search;

import com.example.project_service.repository.search.FreeOrderSearchRepository;
import com.example.project_service.repository.search.FundingOrderSearchRepository;
import com.example.project_service.search.FreeOrderDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class FreeOrderSearchController {

    private final FreeOrderSearchRepository freeOrderRepository;

    private final FundingOrderSearchRepository fundingOrderSearchRepository;

    @GetMapping
    public List<?> search(
    @RequestParam (name ="type") String type,
    @RequestParam(name ="keyword") String keyword){
        switch (type.toLowerCase()){
            case "free":
                return freeOrderRepository.findByTitleContaining(keyword);
            case"funding":
                return fundingOrderSearchRepository.findByTitleContaining(keyword);
            default:
                throw new IllegalArgumentException("지원하지 않는 검색 타입입니다.");
        }
    }
/*
    @GetMapping
    public List<FreeOrderDocument> search(@RequestParam(name = "keyword") String keyword) {
        return FreeOrderRepository.findByTitleContaining(keyword);
    }*/
}