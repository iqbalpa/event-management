package com.example.eventmanagement.service;

import com.example.eventmanagement.model.EventEntity;
import com.example.eventmanagement.model.request.EventRequest;

import java.util.List;

public interface EventService {
    EventEntity getEvent(Long id);

    EventEntity createEvent(EventRequest request);

    List<EventEntity> getEvents(Double price, Integer page, Integer size);

    EventEntity updateEvent(Long id, EventRequest request);

    void deleteEvent(Long id);
}
