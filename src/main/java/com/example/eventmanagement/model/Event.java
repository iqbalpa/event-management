package com.example.eventmanagement.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Event {
    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private String location;
    private Integer capacity;
    private Double price;
    private EventEntity.EventStatus status;
    private UserEntity organizer;
}
