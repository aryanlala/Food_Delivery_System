package com.example.demo.controller;

import com.example.demo.dto.OrderRequest;
import com.example.demo.models.Order;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Place an Order
    @PostMapping("")
    public ResponseEntity<Order> placeOrder(@Valid @RequestBody OrderRequest orderRequest,
                                            Authentication authentication) {
        String email = authentication.getName();
        Order order = orderService.placeOrder(orderRequest, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    // View Order History
    @GetMapping("/history")
    public ResponseEntity<List<Order>> getOrderHistory(Authentication authentication) {
        String email = authentication.getName();
        List<Order> orders = orderService.getOrderHistory(email);
        return ResponseEntity.ok(orders);
    }

}