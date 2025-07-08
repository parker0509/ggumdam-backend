package com.example.project_service.controller.search;

import com.example.project_service.repository.search.FreeOrderSearchRepository;
import com.example.project_service.search.FreeOrderDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search/free")
@RequiredArgsConstructor
public class FreeOrderSearchController {

    private final FreeOrderSearchRepository repository;

    @GetMapping
    public List<FreeOrderDocument> search(@RequestParam(name = "keyword") String keyword) {
        return repository.findByTitleContaining(keyword);
    }
}