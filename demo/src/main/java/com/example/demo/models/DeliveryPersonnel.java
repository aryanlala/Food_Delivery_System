package com.example.demo.models;

import com.example.demo.security.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "DeliveryPersonnel")
@Getter
@Setter

public class DeliveryPersonnel extends User {

    private String contact;
    private String vehicleType;
    private boolean available;

    public DeliveryPersonnel() {}

    public DeliveryPersonnel(String email, String password, String name, Role role, String contact, String vehicleType) {
        super(email, password, role, name);
        this.contact = contact;
        this.vehicleType = vehicleType;
        this.available = true; // default to available
    }

    public DeliveryPersonnel(String email, String password, String name, Role role) {
        super(email, password, role, name);
    }

}
