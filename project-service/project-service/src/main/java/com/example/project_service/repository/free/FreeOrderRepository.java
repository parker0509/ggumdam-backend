package com.example.project_service.repository.free;

import com.example.project_service.domain.free.FreeOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreeOrderRepository extends JpaRepository<FreeOrder, Long> {
}
