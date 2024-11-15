package com.example.demo.repository;

import com.example.demo.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByRestaurantOwnerEmail(String email);

    Restaurant findByRestaurantOwnerEmailAndName(String email, String name);
    // Additional query methods if needed
}
