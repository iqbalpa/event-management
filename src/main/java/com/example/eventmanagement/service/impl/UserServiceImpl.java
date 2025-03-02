package com.example.eventmanagement.service.impl;

import com.example.eventmanagement.model.User;
import com.example.eventmanagement.model.UserEntity;
import com.example.eventmanagement.repository.UserRepository;
import com.example.eventmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(
        UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserDetail(String email) throws NoSuchElementException {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            UserEntity userEntity = optionalUser.get();
            return User.builder()
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .age(userEntity.getAge())
                .gender(userEntity.getGender())
                .build();
        }
        throw new NoSuchElementException("User not found");
    }

    @Override
    public User updateUser(User user) throws NoSuchElementException {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            UserEntity userEntity = optionalUser.get();
            userEntity.setName(user.getName());
            userEntity.setAge(user.getAge());
            userEntity.setGender(user.getGender());
            userRepository.save(userEntity);
            return user;
        }
        throw new NoSuchElementException("User not found");
    }
}
