package com.example.project_service.repository.funding;

import com.example.project_service.domain.funding.FundingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FundingOrderRepository extends JpaRepository<FundingOrder, Long> {
    List<FundingOrder> findByProjectId(Long projectId);
}
