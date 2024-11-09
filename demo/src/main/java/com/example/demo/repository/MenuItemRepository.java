package com.example.demo.repository;

import com.example.demo.models.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByRestaurantId(Long restaurantId);

    // Search Menu Items
    List<MenuItem> findByNameContainingIgnoreCaseAndRestaurant_CuisineTypeContainingIgnoreCaseAndVegetarian(
            String name, String cuisineType, Boolean vegetarian);
}
