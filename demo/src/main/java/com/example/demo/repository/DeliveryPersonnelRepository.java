package com.example.demo.repository;

import com.example.demo.models.Delivery;
import com.example.demo.models.DeliveryPersonnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryPersonnelRepository extends JpaRepository<DeliveryPersonnel, Long> {
    Optional<DeliveryPersonnel> findByEmail(String email);

    Boolean existsByEmail(String email);
}
