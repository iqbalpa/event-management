package com.example.eventmanagement.service.impl;

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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

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
    public EventEntity getEvent(Long id) {
        Optional<EventEntity> eventEntity = eventRepository.findById(id);
        if (eventEntity.isEmpty()) {
            throw new NoSuchElementException("Event not found");
        }
        return eventEntity.get();
    }

    @Override
    public EventEntity createEvent(EventRequest request) {
        String email = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        if (userEntity.isEmpty()) {
            throw new NoSuchElementException("User not found");
        }

        EventEntity eventEntity = EventEntity.builder()
            .title(request.getTitle())
            .description(request.getDescription())
            .startDate(request.getStartDate())
            .endDate(request.getEndDate())
            .location(request.getLocation())
            .price(request.getPrice())
            .status(request.getStatus())
            .organizer(userEntity.get())
            .build();
        eventRepository.save(eventEntity);
        return eventEntity;
    }

    @Override
    public List<EventEntity> getEvents(Double price, Integer page, Integer size) {
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
        return eventEntities.getContent();
    }

    @Override
    public EventEntity updateEvent(Long id, EventRequest request) {
        String email = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Optional<EventEntity> eventEntity = eventRepository.findById(id);
        if (eventEntity.isEmpty()) {
            throw new NoSuchElementException("Event not found");
        }
        if (!eventEntity.get().getOrganizer().getEmail().equals(email)) {
            throw new IllegalArgumentException("Unauthorized access. You are not the organizer of this event");
        }
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        if (userEntity.isEmpty()) {
            throw new NoSuchElementException("User not found");
        }
        eventEntity.get().setTitle(request.getTitle());
        eventEntity.get().setDescription(request.getDescription());
        eventEntity.get().setStartDate(request.getStartDate());
        eventEntity.get().setEndDate(request.getEndDate());
        eventEntity.get().setLocation(request.getLocation());
        eventEntity.get().setPrice(request.getPrice());
        eventEntity.get().setStatus(request.getStatus());
        eventEntity.get().setOrganizer(userEntity.get());
        eventRepository.save(eventEntity.get());
        return eventEntity.get();
    }

    @Override
    public void deleteEvent(Long id) {
        String email = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Optional<EventEntity> eventEntity = eventRepository.findById(id);
        if (eventEntity.isEmpty()) {
            throw new NoSuchElementException("Event not found");
        }
        if (!eventEntity.get().getOrganizer().getEmail().equals(email)) {
            throw new IllegalArgumentException("Unauthorized access. You are not the organizer of this event");
        }
        eventRepository.delete(eventEntity.get());
    }
}
