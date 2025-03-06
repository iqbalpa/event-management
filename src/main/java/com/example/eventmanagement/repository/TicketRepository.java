package com.example.eventmanagement.repository;

import com.example.eventmanagement.model.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<TicketEntity, Long>, TicketCustomRepository {
    Optional<TicketEntity> findByEventIdAndType(Long eventId, TicketEntity.TicketType type);
}
