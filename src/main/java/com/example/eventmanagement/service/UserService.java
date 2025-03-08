package com.example.eventmanagement.service;

import com.example.eventmanagement.model.UserEntity;

import java.util.NoSuchElementException;

public interface UserService {

    UserEntity getUserDetail() throws NoSuchElementException;

    UserEntity updateUser(UserEntity user) throws NoSuchElementException;
}
