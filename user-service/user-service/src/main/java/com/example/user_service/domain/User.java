package com.example.user_service.domain;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Data
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String phone;
    private String password;
    private String email;

    // level 로 구독 서비스 구분 !
    // level
    //      1 -> Free
    //      2 -> subscription
    //      3 -> VIP SUB
    @Enumerated(EnumType.STRING)
    private SubscriptionLevel subscriptionLevel;


}