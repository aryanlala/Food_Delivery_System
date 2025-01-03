package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "MenuItem")
@Getter
@Setter
@JsonIgnoreProperties({"restaurant"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Menu Item Name is mandatory")
    private String name;

    private String description;

    @NotNull(message = "Price is mandatory")
    private Double price;

    @NotNull(message = "Vegetarian field is mandatory")
    private Boolean vegetarian = false;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

}
