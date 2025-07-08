package com.example.project_service.dto.free;

import com.example.project_service.dto.reward.RewardDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FreeOrderDto {
    private Long id;
    private String productName;
    private String shortDescription;
    private String imageUrl;
    private int achievement;
    private int amountRaised;
    private int daysLeft;
    private int supporters;
    private String companyName;
    private int likes;

    private List<RewardDto> rewards;

}
