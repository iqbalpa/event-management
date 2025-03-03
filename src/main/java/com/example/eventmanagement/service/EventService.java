package com.example.eventmanagement.service;

import com.example.eventmanagement.model.Event;
import com.example.eventmanagement.model.request.EventRequest;

import java.util.List;

public interface EventService {
    Event getEvent(Long id);

    Event createEvent(EventRequest request);

    List<Event> getEvents(Double price, Integer page, Integer size);
}
