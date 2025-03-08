package com.example.eventmanagement.component;

import com.example.eventmanagement.model.EventEntity;
import com.example.eventmanagement.model.Gender;
import com.example.eventmanagement.model.TicketEntity;
import com.example.eventmanagement.model.UserEntity;
import com.example.eventmanagement.repository.EventRepository;
import com.example.eventmanagement.repository.TicketRepository;
import com.example.eventmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class DataLoader implements CommandLineRunner {

    private UserRepository userRepository;
    private EventRepository eventRepository;
    private TicketRepository ticketRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(
        UserRepository userRepository,
        EventRepository eventRepository,
        TicketRepository ticketRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.ticketRepository = ticketRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // create users
        userRepository.save(UserEntity.builder()
            .name("admin")
            .email("admin@gmail.com")
            .password(passwordEncoder.encode("admin"))
            .gender(Gender.MALE)
            .role(UserEntity.Role.ADMIN)
            .build());
        userRepository.save(UserEntity.builder()
            .name("admin2")
            .email("admin2@gmail.com")
            .password(passwordEncoder.encode("admin2"))
            .gender(Gender.MALE)
            .role(UserEntity.Role.ADMIN)
            .build());
        userRepository.save(UserEntity.builder()
            .name("user")
            .email("user@gmail.com")
            .password(passwordEncoder.encode("user"))
            .gender(Gender.MALE)
            .role(UserEntity.Role.USER)
            .build());
        // create events
        eventRepository.save(EventEntity.builder()
            .title("Event 1")
            .description("Description 1")
            .location("Jakarta")
            .startDate(new Date("2025/12/12"))
            .status(EventEntity.EventStatus.PUBLISHED)
            .organizer(userRepository.findByEmail("admin2@gmail.com").get())
            .build());
        // create tickets
        ticketRepository.save(TicketEntity.builder()
            .name("Ticket VIP")
            .price(BigDecimal.valueOf(1000000.0))
            .event(eventRepository.findById(1L).get())
            .quantity(10)
            .remainingQuantity(10)
            .salesStartDate(new Date("2025/01/01"))
            .salesEndDate(new Date("2025/12/11"))
            .type(TicketEntity.TicketType.VIP)
            .build());
        ticketRepository.save(TicketEntity.builder()
            .name("Ticket STANDARD")
            .price(BigDecimal.valueOf(100000.0))
            .quantity(100)
            .remainingQuantity(100)
            .event(eventRepository.findById(1L).get())
            .salesStartDate(new Date("2025/01/01"))
            .salesEndDate(new Date("2025/12/11"))
            .type(TicketEntity.TicketType.STANDARD)
            .build());
    }
}
