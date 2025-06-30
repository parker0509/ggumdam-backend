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

    private String productName;
    private int quantity;
    private LocalDateTime orderDate;
    private int participants;
    private int achievement;
    private String companyName;
    private int amountRaised;
    private int daysLeft;
    private String imageUrl;
    private String shortDescription;
    private int supporters;
    private int likes;
    private int goalAmount;
}
