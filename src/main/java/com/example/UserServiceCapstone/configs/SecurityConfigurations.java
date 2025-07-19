package com.example.UserServiceCapstone.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        try {
            http
                    .csrf(csrf -> csrf.disable()) // Disable CSRF for APIs
                    .authorizeHttpRequests(authz -> authz
                            .anyRequest().permitAll() // ✅ Permit all endpoints
                    )
                    .httpBasic(withDefaults());

            return http.build();

        } catch (Exception e) {
            throw new RuntimeException("Failed to build security filter chain", e);
        }
    }
}
