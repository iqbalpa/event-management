package com.example.eventmanagement.controller;

import com.example.eventmanagement.model.Event;
import com.example.eventmanagement.model.request.EventRequest;
import com.example.eventmanagement.model.response.DataEntity;
import com.example.eventmanagement.model.response.ErrorEntity;
import com.example.eventmanagement.model.response.GeneralResponseEntity;
import com.example.eventmanagement.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping
    public ResponseEntity<GeneralResponseEntity<Event>> createEvent(
        @RequestBody EventRequest request
    ) {
        try {
            Event event = eventService.createEvent(request);
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(GeneralResponseEntity.<Event>builder()
                    .message("Event created successfully")
                    .data(DataEntity.<Event>builder()
                        .details(event)
                        .build())
                    .build());
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(GeneralResponseEntity.<Event>builder()
                    .error(ErrorEntity.builder()
                        .code(400)
                        .message(e.getMessage())
                        .build())
                    .build());
        }
    }
}
