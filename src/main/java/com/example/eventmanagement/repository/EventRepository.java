package com.example.eventmanagement.repository;

import com.example.eventmanagement.model.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
}
