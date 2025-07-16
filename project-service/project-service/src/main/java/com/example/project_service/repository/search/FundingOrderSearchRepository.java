package com.example.project_service.repository.search;

import com.example.project_service.search.FreeOrderDocument;
import com.example.project_service.search.FundingOrderDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface FundingOrderSearchRepository extends ElasticsearchRepository<FundingOrderDocument, Long> {

    List<FundingOrderDocument> findByTitleContaining(String keyword);
}
