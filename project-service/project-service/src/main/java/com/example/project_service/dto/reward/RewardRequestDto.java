package com.example.project_service.dto.reward;


import com.example.project_service.domain.reward.Reward;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class RewardRequestDto {
    private String title;
    private String description;
    private int price;
    private int shippingFee;
    private LocalDate deliveryDate;
    private int limitCount;
    private int remaining;

    // 엔티티 변환 메서드 추가
    public Reward toEntity() {
        Reward reward = new Reward();
        reward.setTitle(this.title);
        reward.setDescription(this.description);
        reward.setPrice(this.price);
        reward.setShippingFee(this.shippingFee);
        reward.setDeliveryDate(this.deliveryDate);
        reward.setLimitCount(this.limitCount);
        reward.setRemaining(this.remaining);
        return reward;
    }
}

