package com.example.eventmanagement.service;

import com.example.eventmanagement.model.BookingEntity;
import com.example.eventmanagement.model.request.BookingRequest;

import java.util.List;

public interface BookingService {

    List<BookingEntity> getBookings(String email);

    BookingEntity createBooking(BookingRequest request);

    BookingEntity updateBookingStatus(Long bookingId, String status);

    void deleteBooking(Long bookingId);
}
