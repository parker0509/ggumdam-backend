package com.example.user_service.domain;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user")
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String phone;
    private String password;
    private String email;


    @Enumerated(EnumType.STRING)
    private SubscriptionLevel subscriptionLevel;

    private String profileImageUrl;


}