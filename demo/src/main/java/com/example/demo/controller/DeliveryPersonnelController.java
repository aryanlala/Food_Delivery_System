package com.example.demo.controller;

import com.example.demo.models.Delivery;
import com.example.demo.models.DeliveryPersonnel;
import com.example.demo.service.DeliveryPersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delivery-personnel")
public class DeliveryPersonnelController {

    @Autowired
    private DeliveryPersonnelService personnelService;

    // Register Delivery Personnel
    @PostMapping("/addDetails")
    public ResponseEntity<DeliveryPersonnel> addDetails(Authentication authentication,
            @RequestBody DeliveryPersonnel deliveryPersonnel) {
        String email = authentication.getName();
        DeliveryPersonnel registeredPersonnel = personnelService.addDetails(email, deliveryPersonnel);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredPersonnel);
    }

    // Update Availability
    @PutMapping("/availability")
    public ResponseEntity<DeliveryPersonnel> updateAvailability(Authentication authentication,
            @RequestBody DeliveryPersonnel deliveryPersonnel) {
        String email = authentication.getName();
        DeliveryPersonnel updatedPersonnel = personnelService.updateAvailability(email, deliveryPersonnel);
        return ResponseEntity.ok(updatedPersonnel);
    }

    // View Available Deliveries
    @GetMapping("/available-deliveries")
    public ResponseEntity<List<Delivery>> viewAvailableDeliveries() {
        List<Delivery> availableDeliveries = personnelService.viewAvailableDeliveries();
        return ResponseEntity.ok(availableDeliveries);
    }

    // Accept Delivery
    @PutMapping("/accept-delivery/{deliveryId}")
    public ResponseEntity<Delivery> acceptDelivery(
            @PathVariable Long deliveryId,
            Authentication authentication) {
        String email = authentication.getName();
        Delivery acceptedDelivery = personnelService.acceptDelivery(deliveryId, email);
        return ResponseEntity.ok(acceptedDelivery);
    }

    // Update Delivery Status
    @PutMapping("/update-status/{deliveryId}")
    public ResponseEntity<Delivery> updateDeliveryStatus(
            @PathVariable Long deliveryId,
            @RequestParam String status,
            Authentication authentication) { // Added authentication parameter
        String email = authentication.getName();
        Delivery updatedDelivery = personnelService.updateDeliveryStatus(deliveryId, status, email);
        return ResponseEntity.ok(updatedDelivery);
    }

    // Update Profile
    @PutMapping("/profile")
    public ResponseEntity<DeliveryPersonnel> updateProfile(Authentication authentication,
            @RequestBody DeliveryPersonnel profileUpdate) {
        String email = authentication.getName();
        DeliveryPersonnel updatedProfile = personnelService.updateProfile(email, profileUpdate);
        return ResponseEntity.ok(updatedProfile);
    }
}
