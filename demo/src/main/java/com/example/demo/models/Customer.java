package com.example.demo.models;

import com.example.demo.security.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;



import java.util.*;

@Entity
@Table(name = "Customer")
@Getter
@Setter

public class Customer extends User {

    public Customer(){}


    public Customer(String email, String password, String name, Role role) {
        super(email, password, role , name);
    }

//    @Email
//    @NotBlank(message = "Email is mandatory")
//    @Column(unique = true, nullable = false)
//    private String email;
//
//    @NotBlank(message = "Password is mandatory")
//    private String password;

    // Relationships
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "customer",  cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<PaymentDetails> paymentDetails = new ArrayList<>();

}