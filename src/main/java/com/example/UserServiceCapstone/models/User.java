package com.example.UserServiceCapstone.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "users")
public class User extends BaseModel {
    private String name;
    private String password;
    private String email;
    @ManyToMany
    List<Role> roles; // Assuming Role is another entity class



}
