package com.example.project_service.repository.search;

import com.example.project_service.search.FreeOrderDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface FreeOrderSearchRepository extends ElasticsearchRepository<FreeOrderDocument, Long> {
    List<FreeOrderDocument> findByTitleContaining(String keyword);
}
