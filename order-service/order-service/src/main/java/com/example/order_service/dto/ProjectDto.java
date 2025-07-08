package com.example.order_service.dto;

import lombok.Data;

@Data
public class ProjectDto {
    private Long id;
    private String title;
    private Long price; // 상품 가격
    private Integer stock; // 재고 수량 (있다면)
}
