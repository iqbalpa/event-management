package com.example.eventmanagement.repository;

import com.example.eventmanagement.model.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    List<BookingEntity> findAllByUserEmail(String email);
}
