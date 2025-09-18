package com.example.url_shortener.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.url_shortener.domain.entities.User;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
