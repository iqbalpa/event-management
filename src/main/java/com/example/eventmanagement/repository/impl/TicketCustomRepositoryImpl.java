package com.example.eventmanagement.repository.impl;

import com.example.eventmanagement.model.TicketEntity;
import com.example.eventmanagement.repository.TicketCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TicketCustomRepositoryImpl implements TicketCustomRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TicketCustomRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void decreaseTicketRemainingQuantity(Long eventId, TicketEntity.TicketType type, int quantity) {
        String query = "UPDATE tickets SET remaining_quantity = remaining_quantity - ? WHERE event_id = ? AND type = ?";
        jdbcTemplate.update(query, quantity, eventId, type.name());
    }
}
