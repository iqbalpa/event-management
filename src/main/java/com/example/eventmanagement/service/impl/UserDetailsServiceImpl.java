package com.example.eventmanagement.service.impl;

import com.example.eventmanagement.model.UserEntity;
import com.example.eventmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final String ROLE_PREFIX = "ROLE_";
    private static final String USER_NOT_FOUND_ERROR = "User not found";

    private UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        UserEntity user = userRepository.findByEmail(email)
            .orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND_ERROR));

        return org.springframework.security.core.userdetails.User
            .withUsername(user.getEmail())
            .password(user.getPassword())
            .authorities(getAuthorities(user))
            .accountExpired(false)
            .accountLocked(false)
            .credentialsExpired(false)
            .disabled(false)
            .build();
    }

    private Set<GrantedAuthority> getAuthorities(UserEntity user) {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(ROLE_PREFIX + user.getRole().name());
        return Set.of(authority);
    }
}
