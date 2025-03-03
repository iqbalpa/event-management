package com.example.eventmanagement.service.impl;

import com.example.eventmanagement.model.Event;
import com.example.eventmanagement.model.EventEntity;
import com.example.eventmanagement.model.UserEntity;
import com.example.eventmanagement.model.request.EventRequest;
import com.example.eventmanagement.repository.EventRepository;
import com.example.eventmanagement.repository.UserRepository;
import com.example.eventmanagement.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;
    private UserRepository userRepository;

    @Autowired
    public EventServiceImpl(
        EventRepository eventRepository,
        UserRepository userRepository
    ) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Event createEvent(EventRequest request) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(request.getOrganizerEmail());
        if (userEntity.isEmpty()) {
            throw new NoSuchElementException("User not found");
        }

        EventEntity eventEntity = EventEntity.builder()
            .title(request.getTitle())
            .description(request.getDescription())
            .startDate(request.getStartDate())
            .endDate(request.getEndDate())
            .location(request.getLocation())
            .capacity(request.getCapacity())
            .price(request.getPrice())
            .status(request.getStatus())
            .organizer(userEntity.get())
            .build();
        eventRepository.save(eventEntity);
        return Event.builder()
            .id(eventEntity.getId())
            .title(eventEntity.getTitle())
            .description(eventEntity.getDescription())
            .startDate(eventEntity.getStartDate())
            .endDate(eventEntity.getEndDate())
            .location(eventEntity.getLocation())
            .capacity(eventEntity.getCapacity())
            .price(eventEntity.getPrice())
            .status(eventEntity.getStatus())
            .organizer(UserEntity.builder()
                .email(eventEntity.getOrganizer().getEmail())
                .build())
            .build();
    }
}
