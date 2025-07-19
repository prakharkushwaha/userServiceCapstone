package com.example.UserServiceCapstone.repositories;

import com.example.UserServiceCapstone.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Method to find a user by email
    Optional<User> findByEmail(String email);
}
