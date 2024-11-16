package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Delivery")
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;
    private String status; // e.g., "picked_up", "en_route", "delivered"
    private String delivery_address;

    @ManyToOne
    @JoinColumn(name = "assigned_personnel_id")
    private DeliveryPersonnel assignedPersonnel;
}
