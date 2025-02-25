package com.example.eventmanagement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

@Configuration
public class EncoderConfig {
    @Value("${encryption.salt}")
    private String salt;

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder(10, new SecureRandom(salt.getBytes()));
    }
}
