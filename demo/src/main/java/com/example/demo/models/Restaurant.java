package com.example.demo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
@Table(name = "Restaurant")
@Getter
@Setter
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Restaurant Name is mandatory")
    private String name;

    private String cuisineType;

    private String address;

    private String phoneNumber;

    // Relationships
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<MenuItem> menuItems = new ArrayList<>();


}