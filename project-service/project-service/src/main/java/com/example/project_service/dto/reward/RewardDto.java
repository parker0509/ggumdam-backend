package com.example.project_service.dto.reward;

import com.example.project_service.domain.reward.Reward;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RewardDto {
    private Long id;
    private String title;
    private String desc;
    private int price;
    private int shippingFee;
    private String deliveryDate;
    private int limitCount;
    private int remaining;

    public RewardDto(Reward reward) {
        this.id = reward.getId();
        this.title = reward.getTitle();
        this.desc = reward.getDescription();
        this.price = reward.getPrice();
        this.shippingFee = reward.getShippingFee();
        this.deliveryDate = reward.getDeliveryDate() != null ? reward.getDeliveryDate().toString() : null;
        this.limitCount = reward.getLimitCount();
        this.remaining = reward.getRemaining();
    }


    public static RewardDto from(Reward reward) {
        return RewardDto.builder()
                .id(reward.getId())
                .title(reward.getTitle())
                .desc(reward.getDescription())
                .price(reward.getPrice())
                .shippingFee(reward.getShippingFee())
                .deliveryDate(reward.getDeliveryDate() != null ? reward.getDeliveryDate().toString() : null)
                .limitCount(reward.getLimitCount())
                .remaining(reward.getRemaining())
                .build();
    }
}


