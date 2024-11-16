package com.example.demo.models;

import com.example.demo.security.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "RestaurantOwner")
@Getter
@Setter
public class RestaurantOwner extends User {
    public RestaurantOwner(){}


    public RestaurantOwner(String email, String password, String name, Role role) {
        super(email, password, role , name);
    }

}
