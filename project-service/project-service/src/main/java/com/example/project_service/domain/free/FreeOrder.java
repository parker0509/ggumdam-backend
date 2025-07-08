package com.example.project_service.domain.free;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class FreeOrder {

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
}

