package com.example.demo.controller;

import com.example.demo.models.*;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Get Profile
    @GetMapping("/profile")
    public ResponseEntity<Customer> getCustomerProfile(Authentication authentication) {
        String email = authentication.getName();
        Customer customer = customerService.getCustomerByEmail(email);
        return ResponseEntity.ok(customer);
    }


    // Add Address
    @PostMapping("/addresses")
    public ResponseEntity<Address> addAddress(Authentication authentication,
                                              @Valid @RequestBody Address address) {
        String email = authentication.getName();
        Address createdAddress = customerService.addAddress(email, address);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAddress);
    }

    // Add Payment Details
    @PostMapping("/payment-details")
    public ResponseEntity<PaymentDetails> addPaymentDetails(Authentication authentication,
                                                            @Valid @RequestBody PaymentDetails paymentDetails) {
        String email = authentication.getName();
        PaymentDetails createdPaymentDetails = customerService.addPaymentDetails(email, paymentDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPaymentDetails);
    }

}