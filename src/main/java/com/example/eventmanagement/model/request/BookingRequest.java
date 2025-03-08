package com.example.eventmanagement.model.request;

import com.example.eventmanagement.model.TicketEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
    private Long eventId;
    private TicketEntity.TicketType ticketType;
    private int quantity;
    private String bookingStatus;
}
