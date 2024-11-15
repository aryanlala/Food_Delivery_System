package com.example.demo.dto;

import lombok.Data;

@Data
public class MenuRequest {
    private String name;
    private String description;
    private Double price;
    private Boolean vegetarian;

    public MenuRequest() {
    }

    public MenuRequest(String description, Double price, Boolean vegetarian) {
        this.description = description;
        this.price = price;
        this.vegetarian = vegetarian;
    }
}
