package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RestaurantAccountDTO(
        @JsonProperty("restaurant_name") String restaurantName,
        @JsonProperty("address") String address ,
        @JsonProperty("phone_number") String phoneNumber) {
}
