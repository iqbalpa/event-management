package com.example.eventmanagement.controller;

import com.example.eventmanagement.model.EventEntity;
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
    public ResponseEntity<GeneralResponseEntity<EventEntity>> getEvent(
        @PathVariable String id
    ) {
        try {
            EventEntity event = eventService.getEvent(Long.valueOf(id));
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(GeneralResponseEntity.<EventEntity>builder()
                    .message("Event retrieved successfully")
                    .data(DataEntity.<EventEntity>builder()
                        .details(event)
                        .build())
                    .build());
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(GeneralResponseEntity.<EventEntity>builder()
                    .error(ErrorEntity.builder()
                        .code(400)
                        .message(e.getMessage())
                        .build())
                    .build());
        }
    }

    @PostMapping
    public ResponseEntity<GeneralResponseEntity<EventEntity>> createEvent(
        @RequestBody EventRequest request
    ) {
        try {
            EventEntity event = eventService.createEvent(request);
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(GeneralResponseEntity.<EventEntity>builder()
                    .message("Event created successfully")
                    .data(DataEntity.<EventEntity>builder()
                        .details(event)
                        .build())
                    .build());
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(GeneralResponseEntity.<EventEntity>builder()
                    .error(ErrorEntity.builder()
                        .code(400)
                        .message(e.getMessage())
                        .build())
                    .build());
        }
    }

    @GetMapping
    public ResponseEntity<GeneralResponseEntity<List<EventEntity>>> getEvents(
        @RequestParam(required = false) Double price,
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer size
    ) {
        try {
            List<EventEntity> events = eventService.getEvents(price, page, size);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(GeneralResponseEntity.<List<EventEntity>>builder()
                    .message("Events retrieved successfully")
                    .data(DataEntity.<List<EventEntity>>builder()
                        .details(events)
                        .build())
                    .build());
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(GeneralResponseEntity.<List<EventEntity>>builder()
                    .error(ErrorEntity.builder()
                        .code(400)
                        .message(e.getMessage())
                        .build())
                    .build());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneralResponseEntity<EventEntity>> updateEvent(
        @PathVariable String id,
        @RequestBody EventRequest request
    ) {
        try {
            EventEntity event = eventService.updateEvent(Long.valueOf(id), request);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(GeneralResponseEntity.<EventEntity>builder()
                    .message("Event updated successfully")
                    .data(DataEntity.<EventEntity>builder()
                        .details(event)
                        .build())
                    .build());
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(GeneralResponseEntity.<EventEntity>builder()
                    .error(ErrorEntity.builder()
                        .code(400)
                        .message(e.getMessage())
                        .build())
                    .build());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponseEntity<Void>> deleteEvent(
        @PathVariable String id
    ) {
        try {
            eventService.deleteEvent(Long.valueOf(id));
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(GeneralResponseEntity.<Void>builder()
                    .message("Event deleted successfully")
                    .build());
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(GeneralResponseEntity.<Void>builder()
                    .error(ErrorEntity.builder()
                        .code(400)
                        .message(e.getMessage())
                        .build())
                    .build());
        }
    }
}
