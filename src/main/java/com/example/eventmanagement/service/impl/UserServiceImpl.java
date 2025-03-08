package com.example.eventmanagement.service.impl;

import com.example.eventmanagement.model.UserEntity;
import com.example.eventmanagement.repository.UserRepository;
import com.example.eventmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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
    public UserEntity getUserDetail() throws NoSuchElementException {
        String email = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Optional<UserEntity> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new NoSuchElementException("User not found");
    }

    @Override
    public UserEntity updateUser(UserEntity user) throws NoSuchElementException {
        String email = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Optional<UserEntity> optionalUser = userRepository.findByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            if (!email.equals(user.getEmail())) {
                throw new NoSuchElementException("User not found");
            }
            UserEntity userEntity = optionalUser.get();
            userEntity.setName(user.getName());
            userEntity.setAge(user.getAge());
            userEntity.setGender(user.getGender());
            userRepository.save(userEntity);
            return userEntity;
        }
        throw new NoSuchElementException("User not found");
    }
}
