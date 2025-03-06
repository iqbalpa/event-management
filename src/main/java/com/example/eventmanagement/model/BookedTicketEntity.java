package com.example.eventmanagement.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "booked_tickets")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookedTicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "booking_id")
    private BookingEntity booking;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private TicketEntity ticket;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    public BigDecimal getTotalPrice() {
        return unitPrice.multiply(new BigDecimal(quantity));
    }
}