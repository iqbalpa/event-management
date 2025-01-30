package com.example.eventmanagement.service;

import com.example.eventmanagement.exception.LoginException;

public interface AuthService {
    String login(String email, String password) throws LoginException;
    String register(String name, String email, String password);
}
