package com.example.demo.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {

    @NotNull(message = "Restaurant ID is mandatory")
    private Long restaurantId;

    @NotNull(message = "Address ID is mandatory")
    private Long addressId;

    @NotNull(message = "Payment Details ID is mandatory")
    private Long paymentDetailsId;

    @NotEmpty(message = "Order items cannot be empty")
    private List<OrderItemRequest> items;

    // Getters and Setters
    // ...
}
