package com.example.project_service.dto.funding;

import com.example.project_service.dto.reward.RewardDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FundingDto {
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
    private LocalDateTime FreeOrderDate;
    private List<RewardDto> rewards;

}
