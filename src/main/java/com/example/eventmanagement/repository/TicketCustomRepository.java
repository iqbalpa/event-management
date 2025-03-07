package com.example.eventmanagement.repository;

import com.example.eventmanagement.model.TicketEntity;

public interface TicketCustomRepository {
    void decreaseTicketRemainingQuantity(Long eventId, TicketEntity.TicketType type, int quantity);
}
