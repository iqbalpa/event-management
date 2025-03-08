package com.example.eventmanagement.service;

import com.example.eventmanagement.model.User;

import java.util.NoSuchElementException;

public interface UserService {
    User getUserDetail() throws NoSuchElementException;

    User updateUser(User user) throws NoSuchElementException;
}
