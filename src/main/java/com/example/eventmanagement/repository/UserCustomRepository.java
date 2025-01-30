package com.example.eventmanagement.repository;

import com.example.eventmanagement.model.UserEntity;

import java.util.List;

public interface UserCustomRepository {

    List<UserEntity> findUserEntitiesUsingGmail();
}
