package com.example.demo.models;

import com.example.demo.security.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // Constructors, Getters, and Setters
    public User() {}

    public User(String email, String password, Role role, String name) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.name = name;
    }
}