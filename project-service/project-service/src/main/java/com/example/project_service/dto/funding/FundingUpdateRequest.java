package com.example.project_service.dto.funding;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FundingUpdateRequest {

    private Long rewardId;
    private int paidAmount;

    // getters, setters
}
