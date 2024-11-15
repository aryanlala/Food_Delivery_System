package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.models.Customer;
import com.example.demo.models.RestaurantOwner;
import com.example.demo.models.User;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.RestaurantOwnerRepository;
import com.example.demo.security.JwtTokenUtil;
import com.example.demo.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RestaurantOwnerRepository restaurantOwnerRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    // Login Endpoint
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        String jwt = jwtTokenUtil.generateToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    // Register Endpoint
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (customerRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Email address already in use.");
        }

        String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());

        //Similarly needed to add other roles
        if(signUpRequest.getRole() == Role.CUSTOMER){
            Customer customer = new Customer(
                    signUpRequest.getEmail(),
                    encodedPassword,
                    signUpRequest.getName(),
                    Role.CUSTOMER);
            customer.setPaymentDetails(null); // Set payment details to null
            customer.setAddresses(null);      // Set addresses to null
            customerRepository.save(customer);
        }

        if(signUpRequest.getRole() == Role.RESTAURANT_OWNER){
            RestaurantOwner restaurantOwner = new RestaurantOwner(
                    signUpRequest.getEmail(),
                    encodedPassword,
                    signUpRequest.getName(),
                    Role.RESTAURANT_OWNER);// Set addresses to null
            restaurantOwnerRepository.save(restaurantOwner);
        }

        return ResponseEntity.ok("User registered successfully");
    }
}