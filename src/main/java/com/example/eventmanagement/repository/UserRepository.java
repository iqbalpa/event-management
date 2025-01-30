package com.example.eventmanagement.repository;

import com.example.eventmanagement.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>, UserCustomRepository {
    Optional<UserEntity> findByEmailContains(String email);

    Optional<UserEntity> findByEmail(String email);
}
