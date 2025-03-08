package com.example.eventmanagement.service;

import com.example.eventmanagement.exception.LoginException;
import com.example.eventmanagement.exception.RegisterException;
import com.example.eventmanagement.model.request.RegisterRequest;

public interface AuthService {
    String login(String email, String password) throws LoginException;

    String register(RegisterRequest request) throws RegisterException;
}
