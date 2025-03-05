package com.example.eventmanagement.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Entity
@Table(name = "events")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "created_at")
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "location", length = 100)
    private String location;

    @Column(name = "capacity")
    private Integer capacity;

    private Integer remainingCapacity;

    @Column(name = "price")
    private Double price;

    @Column(name = "event_status")
    @Enumerated(EnumType.STRING)
    private EventStatus status = EventStatus.DRAFT;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organizer_email", referencedColumnName = "email")
    private UserEntity organizer;

    public enum EventStatus {
        DRAFT, PUBLISHED, CANCELLED, COMPLETED
    }
}
