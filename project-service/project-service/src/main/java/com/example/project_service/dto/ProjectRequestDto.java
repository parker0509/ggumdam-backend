package com.example.project_service.dto;

import com.example.project_service.domain.Project;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProjectRequestDto {
    @NotBlank(message = "프로젝트 이름은 필수입니다.")
    private String name;

    @NotBlank(message = "설명은 필수입니다.")
    private String description;

    @NotNull(message = "목표 금액을 입력해주세요.")
    private BigDecimal goalAmount;

    private String imageUrl; // 이미지 URL은 선택 사항

    private List<String> tags;
    
    private String email; // ✅ 이메일 필드 추가
}

