package com.example.eventmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "bookings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Column(unique = true, updatable = false)
    private String bookingNumber = UUID.randomUUID().toString();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email")
    private UserEntity user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "event_id")
    private EventEntity event;

    @JsonIgnore
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BookedTicketEntity> bookedTickets = new HashSet<>();

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private BookingStatus status = BookingStatus.PENDING;

    private BigDecimal totalAmount;

    private String paymentId;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    private String qrCodeUrl;

    private Date cancelledAt;

    private String cancellationReason;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updatedAt;

    public enum BookingStatus {
        PENDING, CONFIRMED, CANCELLED, COMPLETED
    }

    public enum PaymentStatus {
        PENDING, PAID, REFUNDED, FAILED
    }
}