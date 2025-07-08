package com.example.project_service.dto;

import com.example.project_service.domain.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectResponseDto {
    private Long id;
    private String title;
    private String description;         // ✅ 설명 필드 추가
    private String imageUrl;
    private String creatorName;
    private String fundingStatus;
    private int goalAmount;             // ✅ 목표 금액
    private int participants;           // ✅ 참여자 수
    private List<String> tags;

    public static ProjectResponseDto fromEntity(Project project) {

        String fundingStatus = calculateFundingPercentage(project.getRaisedAmount(), project.getGoalAmount());

        return ProjectResponseDto.builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription()) // ✅ 매핑 추가
                .imageUrl(project.getImageUrl())
                .creatorName(project.getCreatorName())
                .fundingStatus(fundingStatus)
                .goalAmount(project.getGoalAmount().intValue()) // ✅ BigDecimal → int
                .participants(project.getParticipants())        // ✅ 매핑 추가
                .tags(project.getTags())
                .build();
    }

    private static String calculateFundingPercentage(BigDecimal raised, BigDecimal goal) {
        if (goal == null || goal.compareTo(BigDecimal.ZERO) == 0) {
            return "0% 달성";
        }
        BigDecimal percentage = raised
                .multiply(BigDecimal.valueOf(100))
                .divide(goal, 0, BigDecimal.ROUND_HALF_UP);
        return percentage.toPlainString() + "% 달성";
    }
}

