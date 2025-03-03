package com.example.eventmanagement.model.request;

import com.example.eventmanagement.model.EventEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventRequest {
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private String location;
    private Integer capacity;
    private Double price;
    private EventEntity.EventStatus status;
    private String organizerEmail;
}
