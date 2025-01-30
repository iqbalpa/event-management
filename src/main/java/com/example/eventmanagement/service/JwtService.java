package com.example.eventmanagement.service;

public interface JwtService {
    String generateToken(String username, String email);
    boolean validateToken(String token);
}
