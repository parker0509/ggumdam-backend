package com.example.project_service.search;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "free_orders")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class FreeOrderDocument {
    @Id
    private Long id;
    private String title;
    private String summary;
    private String companyName;
    private String imageUrl;
}
