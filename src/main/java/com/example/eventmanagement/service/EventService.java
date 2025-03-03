package com.example.eventmanagement.service;

import com.example.eventmanagement.model.Event;
import com.example.eventmanagement.model.request.EventRequest;

public interface EventService {
    Event createEvent(EventRequest request);
}
