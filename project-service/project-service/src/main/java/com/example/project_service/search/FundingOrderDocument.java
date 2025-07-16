package com.example.project_service.search;

import jakarta.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;
import lombok.*;

@Document(indexName = "funding_order")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class FundingOrderDocument {
    @Id
    private Long id;
    private String title;
    private String summary;
    private String companyName;
    private String imageUrl;
}
