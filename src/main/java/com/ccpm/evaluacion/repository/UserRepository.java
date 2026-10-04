package com.ccpm.evaluacion.repository;

import com.ccpm.evaluacion.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}