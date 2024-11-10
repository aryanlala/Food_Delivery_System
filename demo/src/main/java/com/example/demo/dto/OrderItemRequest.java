package com.example.demo.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderItemRequest {

    @NotNull(message = "Menu Item ID is mandatory")
    private Long menuItemId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

}
