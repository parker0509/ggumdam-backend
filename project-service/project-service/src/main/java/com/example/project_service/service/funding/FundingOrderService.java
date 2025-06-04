package com.example.project_service.service.funding;


import com.example.project_service.domain.funding.FundingOrder;
import com.example.project_service.repository.funding.FundingOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FundingOrderService {

    private final FundingOrderRepository fundingOrderRepository;

    // 펀딩 성공 여부 체크 후 주문 생성
    public FundingOrder createFundingOrder(FundingOrder order) {
        // 예시 로직: 외부에서 펀딩 성공 여부 판단된 경우만 저장
        if (checkProjectFundingSuccess(order.getProjectId())) {
            order.setStatus(FundingOrder.FundingStatus.WAITING_PAYMENT);
            order.setCreatedAt(LocalDateTime.now());
            return fundingOrderRepository.save(order);
        } else {
            throw new IllegalStateException("이 프로젝트는 아직 펀딩에 성공하지 않았습니다.");
        }
    }

    public List<FundingOrder> getOrdersByProject(Long projectId) {
        return fundingOrderRepository.findByProjectId(projectId);
    }

    // 임시 예시 메서드
    private boolean checkProjectFundingSuccess(Long projectId) {
        // 실제로는 project-service API 호출해서 체크
        return true; // 지금은 성공한 걸로 가정
    }
}
