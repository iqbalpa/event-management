package com.example.eventmanagement.service;

import com.example.eventmanagement.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    User saveUser(User user);
}
