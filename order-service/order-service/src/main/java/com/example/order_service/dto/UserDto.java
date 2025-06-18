package com.example.order_service.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private Integer point; // 보유 포인트 등 필요 시
}