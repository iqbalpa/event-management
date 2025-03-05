package com.example.eventmanagement.repository;

import com.example.eventmanagement.model.EventEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
    Page<EventEntity> findAllByPriceIsLessThanEqual(Double priceIsLessThan, Pageable pageable);
}
