package com.example.eventmanagement.service.impl;

import com.example.eventmanagement.model.Event;
import com.example.eventmanagement.model.EventEntity;
import com.example.eventmanagement.model.UserEntity;
import com.example.eventmanagement.model.request.EventRequest;
import com.example.eventmanagement.repository.EventRepository;
import com.example.eventmanagement.repository.UserRepository;
import com.example.eventmanagement.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private static final Integer DEFAULT_PAGE = 0;
    private static final Integer DEFAULT_SIZE = 10;

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
    public Event getEvent(Long id) {
        Optional<EventEntity> eventEntity = eventRepository.findById(id);
        if (eventEntity.isEmpty()) {
            throw new NoSuchElementException("Event not found");
        }
        return Event.builder()
            .id(eventEntity.get().getId())
            .title(eventEntity.get().getTitle())
            .description(eventEntity.get().getDescription())
            .startDate(eventEntity.get().getStartDate())
            .endDate(eventEntity.get().getEndDate())
            .location(eventEntity.get().getLocation())
            .capacity(eventEntity.get().getCapacity())
            .price(eventEntity.get().getPrice())
            .status(eventEntity.get().getStatus())
            .organizer(UserEntity.builder()
                .email(eventEntity.get().getOrganizer().getEmail())
                .build())
            .build();
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

    @Override
    public List<Event> getEvents(Double price, Integer page, Integer size) {
        // if no page or size is provided, use default values
        if (page == null) {
            page = DEFAULT_PAGE;
        }
        if (size == null) {
            size = DEFAULT_SIZE;
        }
        Pageable pageable = PageRequest.of(page, size);
        // if no price is provided, return all events
        Page<EventEntity> eventEntities = null;
        if (price == null) {
            eventEntities = eventRepository.findAll(pageable);
        } else {
            eventEntities = eventRepository.findAllByPriceIsLessThanEqual(price, pageable);
        }
        return eventEntities.stream().map(eventEntity -> Event.builder()
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
                .build())
            .toList();
    }
}
