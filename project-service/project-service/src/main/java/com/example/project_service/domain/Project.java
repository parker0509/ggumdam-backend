package com.example.project_service.domain;

import com.example.project_service.util.StringListConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.userdetails.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor  // 기본 생성자 추가
@AllArgsConstructor // (선택) 전체 필드 생성자
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    private String description;

    private String imageUrl;

    private int participants;

    @Column(nullable = false)
    private BigDecimal goalAmount;

    private BigDecimal raisedAmount = BigDecimal.ZERO;

    private String creatorName;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    public enum ProjectStatus {
        FUNDING, SUCCESS, FAILED
    }

    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<String> tags;




}
