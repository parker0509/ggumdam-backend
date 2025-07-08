package com.example.project_service.domain.funding;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FundingOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    private int goalAmount; // 목표 금액
    // getters & setters
}
