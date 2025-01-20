package com.alura.forohub.forohub.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alura.forohub.forohub.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  Optional<User> findByEmail(String email);
}
