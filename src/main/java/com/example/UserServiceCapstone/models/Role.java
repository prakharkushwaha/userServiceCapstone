package com.example.UserServiceCapstone.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="roles")
public class Role extends BaseModel {
    private String Value;
}
