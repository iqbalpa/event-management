package com.example.eventmanagement.repository;

import com.example.eventmanagement.model.BookedTicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookedTicketRepository extends JpaRepository<BookedTicketEntity, Long> {
}
