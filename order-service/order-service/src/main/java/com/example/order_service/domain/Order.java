package com.example.order_service.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long userId;
    @NotNull
    private Long rewardId;
    @NotNull
    private int quantity;
    @NotNull
    private int totalAmount;
    private String recipient;
    private String phone;
    private String address;
    private String addressDetail;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime localDateTime;
    private LocalDateTime updateAt;
}
