package com.example.project_service.mapper;

import com.example.project_service.domain.free.FreeOrder;
import com.example.project_service.search.FreeOrderDocument;
import lombok.*;

public class FreeOrderMapper {
    public static FreeOrderDocument toDocument(FreeOrder freeOrder) {
        return FreeOrderDocument.builder()
                .id(freeOrder.getId())
                .title(freeOrder.getProductName())
                .companyName(freeOrder.getCompanyName())
                .build();
    }
}
