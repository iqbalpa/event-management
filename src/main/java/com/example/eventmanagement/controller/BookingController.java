package com.example.eventmanagement.controller;

import com.example.eventmanagement.model.BookingEntity;
import com.example.eventmanagement.model.Event;
import com.example.eventmanagement.model.request.BookingRequest;
import com.example.eventmanagement.model.request.EventRequest;
import com.example.eventmanagement.model.response.DataEntity;
import com.example.eventmanagement.model.response.ErrorEntity;
import com.example.eventmanagement.model.response.GeneralResponseEntity;
import com.example.eventmanagement.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/events/{id}/booking")
    public ResponseEntity<GeneralResponseEntity<BookingEntity>> createBooking(
        @RequestBody BookingRequest request,
        @PathVariable String id
    ) {
        try {
            request.setEventId(Long.valueOf(id));
            BookingEntity booking = bookingService.createBooking(request);
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(GeneralResponseEntity.<BookingEntity>builder()
                    .message("Booking created successfully")
                    .data(DataEntity.<BookingEntity>builder()
                        .details(booking)
                        .build())
                    .build());
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(GeneralResponseEntity.<BookingEntity>builder()
                    .error(ErrorEntity.builder()
                        .code(400)
                        .message(e.getMessage())
                        .build())
                    .build());
        }
    }

    @GetMapping("/booking")
    public ResponseEntity<GeneralResponseEntity<List<BookingEntity>>> getBooking() {
        try {
            List<BookingEntity> bookings = bookingService.getBookings();
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(GeneralResponseEntity.<List<BookingEntity>>builder()
                    .message("Bookings retrieved successfully")
                    .data(DataEntity.<List<BookingEntity>>builder()
                        .details(bookings)
                        .build())
                    .build());
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(GeneralResponseEntity.<List<BookingEntity>>builder()
                    .error(ErrorEntity.builder()
                        .code(400)
                        .message(e.getMessage())
                        .build())
                    .build());
        }
    }

    @PutMapping("/booking/{id}")
    public ResponseEntity<GeneralResponseEntity<BookingEntity>> updateBookingStatus(
        @PathVariable String id,
        @RequestBody BookingRequest request
    ) {
        try {
            BookingEntity booking = bookingService.updateBookingStatus(Long.valueOf(id), request.getBookingStatus());
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(GeneralResponseEntity.<BookingEntity>builder()
                    .message("Booking updated successfully")
                    .data(DataEntity.<BookingEntity>builder()
                        .details(booking)
                        .build())
                    .build());
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(GeneralResponseEntity.<BookingEntity>builder()
                    .error(ErrorEntity.builder()
                        .code(400)
                        .message(e.getMessage())
                        .build())
                    .build());
        }
    }

    @DeleteMapping("/booking/{id}")
    public ResponseEntity<GeneralResponseEntity<Void>> deleteBooking(
        @PathVariable String id
    ) {
        try {
            bookingService.deleteBooking(Long.valueOf(id));
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(GeneralResponseEntity.<Void>builder()
                    .message("Booking deleted successfully")
                    .data(DataEntity.<Void>builder().build())
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
