package com.example.demo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "PaymentDetails")
@Getter
@Setter
public class PaymentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Card Number is mandatory")
    @Size(min = 13, max = 19)
    private String cardNumber;

    @NotBlank(message = "Card Holder Name is mandatory")
    private String cardHolderName;

    @NotBlank(message = "Expiry Date is mandatory")
    @Pattern(regexp = "^(0[1-9]|1[0-2])/([0-9]{4})$", message = "Expiry date must be in MM/YYYY format")
    private String expiryDate;

    @NotBlank(message = "CVV is mandatory")
    @Size(min = 3, max = 4)
    private String cvv;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

}