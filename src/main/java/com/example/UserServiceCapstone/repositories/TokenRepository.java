package com.example.UserServiceCapstone.repositories;

import com.example.UserServiceCapstone.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Long> {

    Token save(Token token);
      Optional<Token> findByValue(String value);
}
