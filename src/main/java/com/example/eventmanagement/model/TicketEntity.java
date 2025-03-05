package com.example.eventmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "tickets")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "remaining_quantity")
    private Integer remainingQuantity;

    @ManyToOne
    @JoinColumn(name = "event_id")
    @JsonIgnore
    private EventEntity event;

    @Enumerated(EnumType.STRING)
    private TicketType type = TicketType.STANDARD;

    private Date salesStartDate;

    private Date salesEndDate;

    public enum TicketType {
        STANDARD, VIP, EARLY_BIRD, STUDENT
    }

    // Helper methods
    public boolean isAvailable() {
        Date now = new Date();
        return remainingQuantity > 0 &&
            (salesStartDate == null || now.after(salesStartDate)) &&
            (salesEndDate == null || now.before(salesEndDate));
    }
}
