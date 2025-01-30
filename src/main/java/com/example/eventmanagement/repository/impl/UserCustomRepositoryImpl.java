package com.example.eventmanagement.repository.impl;

import com.example.eventmanagement.model.UserEntity;
import com.example.eventmanagement.repository.UserCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserCustomRepositoryImpl implements UserCustomRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserCustomRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<UserEntity> findUserEntitiesUsingGmail() {
        String query = "SELECT * FROM users WHERE email ENDS WITH 'gmail.com'";
        return jdbcTemplate.query(query, (rs, rowNum) -> UserEntity.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .build());
    }
}
