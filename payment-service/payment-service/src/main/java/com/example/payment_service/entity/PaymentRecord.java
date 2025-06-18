package com.example.payment_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRecord {

    @Id
    private String impUid;

    private Long amount;

    private String status;

    private String payMethod;

    private String buyerName;

    private String buyerEmail;
}