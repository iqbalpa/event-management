package com.example.eventmanagement.service.impl;

import com.example.eventmanagement.model.*;
import com.example.eventmanagement.model.request.BookingRequest;
import com.example.eventmanagement.repository.*;
import com.example.eventmanagement.service.BookingService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    @Transactional
    @Override
    public BookingEntity createBooking(BookingRequest request) {
        try {
            Optional<UserEntity> user = userRepository.findByEmail(request.getUserEmail());
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
                .totalAmount(bookedTicket.getUnitPrice().multiply(BigDecimal.valueOf(bookedTicket.getQuantity())))
                .build();
            booking.getBookedTickets().add(bookedTicket);
            bookedTicket.setBooking(booking);
            bookingRepository.save(booking);
            bookedTicketRepository.save(bookedTicket);
            return booking;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid request");
        }
    }
}
