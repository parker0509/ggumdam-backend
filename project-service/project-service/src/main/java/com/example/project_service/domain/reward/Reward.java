package com.example.project_service.domain.reward;

import com.example.project_service.domain.free.FreeOrder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private int price;
    private int shippingFee;
    private LocalDate deliveryDate;
    private int remaining;
    private int limitCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "free_order_id")
    private FreeOrder freeOrder;
}

