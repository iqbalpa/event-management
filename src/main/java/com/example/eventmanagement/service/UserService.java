package com.example.eventmanagement.service;

import com.example.eventmanagement.model.User;

import java.util.NoSuchElementException;

public interface UserService {
    User getUserDetail(String email) throws NoSuchElementException;
}
