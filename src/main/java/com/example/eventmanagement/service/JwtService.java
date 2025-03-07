package com.example.eventmanagement.service;

public interface JwtService {

    String generateToken(String name, String email, String role);

    boolean validateToken(String token);
}
