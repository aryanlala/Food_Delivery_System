package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.models.Delivery;
import com.example.demo.models.DeliveryPersonnel;
import com.example.demo.repository.DeliveryPersonnelRepository;
import com.example.demo.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class DeliveryPersonnelService {

    @Autowired
    private DeliveryPersonnelRepository deliveryPersonnelRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    public DeliveryPersonnel addDetails(String email, DeliveryPersonnel details) {
        DeliveryPersonnel personnel = deliveryPersonnelRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Delivery Personnel not found"));

        personnel.setVehicleType(details.getVehicleType());
        personnel.setContact(details.getContact());
        personnel.setAvailable(details.isAvailable());

        return deliveryPersonnelRepository.save(personnel);
    }

    public DeliveryPersonnel updateAvailability(String email, DeliveryPersonnel details) {
        DeliveryPersonnel personnel = deliveryPersonnelRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Delivery Personnel not found"));

        personnel.setAvailable(details.isAvailable());
        return deliveryPersonnelRepository.save(personnel);
    }

    public List<Delivery> viewAvailableDeliveries() {
        try {
            // Find all deliveries that are pending and not assigned
            List<Delivery> availableDeliveries = deliveryRepository.findByStatusAndAssignedPersonnelIsNull("PENDING");

            if (availableDeliveries.isEmpty()) {
                throw new IllegalStateException("No deliveries available at the moment");
            }

            return availableDeliveries;
        } catch (IllegalStateException e) {
            throw e;  // Re-throw IllegalStateException as is
        } catch (Exception e) {
            throw new RuntimeException("Error fetching available deliveries: " + e.getMessage());
        }
    }

    public Delivery acceptDelivery(Long deliveryId, String email) {
        // Find the delivery
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery", "id", deliveryId.toString()));

        // Check if delivery is already assigned
        if (delivery.getAssignedPersonnel() != null) {
            throw new IllegalStateException("Delivery is already assigned to a delivery personnel");
        }

        // Find the delivery personnel
        DeliveryPersonnel personnel = deliveryPersonnelRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery Personnel", "email", email));

        // Assign the delivery
        delivery.setAssignedPersonnel(personnel);
        delivery.setStatus("ASSIGNED");

        return deliveryRepository.save(delivery);
    }

    public Delivery updateDeliveryStatus(Long deliveryId, String status, String email) {
        // Find the delivery
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery", "id", deliveryId.toString()));

        if (!delivery.getAssignedPersonnel().getEmail().equals(email)) {
            throw new IllegalStateException(
                    "Cannot update delivery status: delivery not assigned to current personnel.");
        }

        // Validate and update the status
        if (!isValidStatus(status)) {
            throw new IllegalStateException("Invalid delivery status: " + status);
        }

        delivery.setStatus(status.toUpperCase());
        return deliveryRepository.save(delivery);
    }

    private boolean isValidStatus(String status) {
        List<String> validStatuses = Arrays.asList(
                "PENDING", "ASSIGNED", "PICKED_UP", "EN_ROUTE", "DELIVERED", "CANCELLED");
        return validStatuses.contains(status.toUpperCase());
    }

    public DeliveryPersonnel updateProfile(String email, DeliveryPersonnel updatedPersonnel) {
        DeliveryPersonnel existingPersonnel = deliveryPersonnelRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Delivery Personnel not found"));

        // Update profile fields while preserving sensitive data
        existingPersonnel.setName(updatedPersonnel.getName());
        existingPersonnel.setContact(updatedPersonnel.getContact());
        existingPersonnel.setVehicleType(updatedPersonnel.getVehicleType());

        return deliveryPersonnelRepository.save(existingPersonnel);
    }

}
