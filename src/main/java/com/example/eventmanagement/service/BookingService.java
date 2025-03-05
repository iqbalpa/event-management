package com.example.eventmanagement.service;

import com.example.eventmanagement.model.BookingEntity;
import com.example.eventmanagement.model.request.BookingRequest;

public interface BookingService {
    BookingEntity createBooking(BookingRequest request);
}
