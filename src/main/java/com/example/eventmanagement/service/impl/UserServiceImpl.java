package com.example.eventmanagement.service.impl;

import com.example.eventmanagement.model.User;
import com.example.eventmanagement.model.UserEntity;
import com.example.eventmanagement.repository.UserRepository;
import com.example.eventmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream()
                .map(userEntity -> User.builder()
                        .name(userEntity.getName())
                        .email(userEntity.getEmail())
                        .password(userEntity.getPassword())
                        .build())
                .toList();
    }

    @Override
    public User saveUser(User user) {
        UserEntity userEntity = UserEntity.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
        userEntity = userRepository.save(userEntity);
        return User.builder()
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .build();
    }
}
