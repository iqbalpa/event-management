package com.example.eventmanagement.service.impl;

import com.example.eventmanagement.model.*;
import com.example.eventmanagement.model.request.BookingRequest;
import com.example.eventmanagement.repository.*;
import com.example.eventmanagement.service.BookingService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    private BookingRepository bookingRepository;
    private BookedTicketRepository bookedTicketRepository;
    private UserRepository userRepository;
    private EventRepository eventRepository;
    private TicketRepository ticketRepository;

    @Autowired
    public BookingServiceImpl(
        BookingRepository bookingRepository,
        UserRepository userRepository,
        EventRepository eventRepository,
        TicketRepository ticketRepository,
        BookedTicketRepository bookedTicketRepository
    ) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.ticketRepository = ticketRepository;
        this.bookedTicketRepository = bookedTicketRepository;
    }

    @Override
    public List<BookingEntity> getBookings() {
        String email = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        return bookingRepository.findAllByUserEmail(email);
    }

    @Transactional
    @Override
    public BookingEntity createBooking(BookingRequest request) {
        String email = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        try {
            Optional<UserEntity> user = userRepository.findByEmail(email);
            Optional<EventEntity> event = eventRepository.findById(request.getEventId());
            Optional<TicketEntity> ticket = ticketRepository.findByEventIdAndType(request.getEventId(), request.getTicketType());

            // check event and ticket status
            if (!event.get().isAvailable() || !ticket.get().isAvailable()) {
                throw new IllegalArgumentException("Event is not available for booking");
            }
            // create booking
            BookedTicketEntity bookedTicket = BookedTicketEntity.builder()
                .ticket(ticket.get())
                .quantity(request.getQuantity())
                .unitPrice(ticket.get().getPrice())
                .build();
            BookingEntity booking = BookingEntity.builder()
                .user(user.get())
                .event(event.get())
                .bookedTickets(new HashSet<>())
                .totalAmount(bookedTicket.getUnitPrice().multiply(BigDecimal.valueOf(bookedTicket.getQuantity())))
                .build();
            bookedTicket.setBooking(booking);
            bookingRepository.save(booking);
            bookedTicketRepository.save(bookedTicket);
            ticketRepository.decreaseTicketRemainingQuantity(
                request.getEventId(),
                request.getTicketType(),
                request.getQuantity());
            return booking;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid request");
        }
    }

    @Override
    public BookingEntity updateBookingStatus(Long bookingId, String status) {
        String email = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Optional<BookingEntity> booking = bookingRepository.findById(bookingId);
        if (booking.isPresent()) {
            if (!booking.get().getUser().getEmail().equals(email)) {
                throw new IllegalArgumentException("Unauthorized access");
            }
            booking.get().setStatus(BookingEntity.BookingStatus.valueOf(status));
            return bookingRepository.save(booking.get());
        }
        throw new IllegalArgumentException("Booking not found");
    }

    @Override
    public void deleteBooking(Long bookingId) {
        String email = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Optional<BookingEntity> booking = bookingRepository.findById(bookingId);
        if (booking.isPresent()) {
            if (!booking.get().getUser().getEmail().equals(email)) {
                throw new IllegalArgumentException("Unauthorized access");
            }
            bookingRepository.delete(booking.get());
        }
        throw new IllegalArgumentException("Booking not found");
    }
}
