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
        return eventBuilder(eventEntity.get());
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
        return eventBuilder(eventEntity);
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
        return eventEntities.stream()
            .map(this::eventBuilder)
            .toList();
    }

    @Override
    public Event updateEvent(Long id, EventRequest request) {
        Optional<EventEntity> eventEntity = eventRepository.findById(id);
        if (eventEntity.isEmpty()) {
            throw new NoSuchElementException("Event not found");
        }
        Optional<UserEntity> userEntity = userRepository.findByEmail(request.getOrganizerEmail());
        if (userEntity.isEmpty()) {
            throw new NoSuchElementException("User not found");
        }
        eventEntity.get().setTitle(request.getTitle());
        eventEntity.get().setDescription(request.getDescription());
        eventEntity.get().setStartDate(request.getStartDate());
        eventEntity.get().setEndDate(request.getEndDate());
        eventEntity.get().setLocation(request.getLocation());
        eventEntity.get().setCapacity(request.getCapacity());
        eventEntity.get().setPrice(request.getPrice());
        eventEntity.get().setStatus(request.getStatus());
        eventEntity.get().setOrganizer(userEntity.get());
        eventRepository.save(eventEntity.get());
        return eventBuilder(eventEntity.get());
    }

    @Override
    public void deleteEvent(Long id) {
        Optional<EventEntity> eventEntity = eventRepository.findById(id);
        if (eventEntity.isEmpty()) {
            throw new NoSuchElementException("Event not found");
        }
        eventRepository.delete(eventEntity.get());
    }

    private Event eventBuilder(EventEntity eventEntity) {
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
