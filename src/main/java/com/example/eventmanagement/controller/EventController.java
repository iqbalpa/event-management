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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponseEntity<Event>> getEvent(
        @PathVariable String id
    ) {
        try {
            Event event = eventService.getEvent(Long.valueOf(id));
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(GeneralResponseEntity.<Event>builder()
                    .message("Event retrieved successfully")
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

    @GetMapping
    public ResponseEntity<GeneralResponseEntity<List<Event>>> getEvents(
        @RequestParam(required = false) Double price,
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer size
    ) {
        try {
            List<Event> events = eventService.getEvents(price, page, size);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(GeneralResponseEntity.<List<Event>>builder()
                    .message("Events retrieved successfully")
                    .data(DataEntity.<List<Event>>builder()
                        .details(events)
                        .build())
                    .build());
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(GeneralResponseEntity.<List<Event>>builder()
                    .error(ErrorEntity.builder()
                        .code(400)
                        .message(e.getMessage())
                        .build())
                    .build());
        }
    }
}
