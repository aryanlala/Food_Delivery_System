package com.example.demo.controller;

import com.example.demo.dto.OrderRequest;
import com.example.demo.models.*;
import com.example.demo.service.CustomerService;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

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

    // Place an Order
    @PostMapping("/orders")
    public ResponseEntity<Order> placeOrder(@Valid @RequestBody OrderRequest orderRequest,
                                            Authentication authentication) {
        String email = authentication.getName();
        Order order = orderService.placeOrder(orderRequest, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    // View Order History
    @GetMapping("/orders/history")
    public ResponseEntity<List<Order>> getOrderHistory(Authentication authentication) {
        String email = authentication.getName();
        List<Order> orders = orderService.getOrderHistory(email);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/orders/{orderId}/status")
    public ResponseEntity<String> trackOrder(Authentication authentication, @PathVariable Long orderId ) {
        String email = authentication.getName();
        String status = orderService.getOrderStatusByOrderId(orderId,email);
        return ResponseEntity.ok(status);
    }

}