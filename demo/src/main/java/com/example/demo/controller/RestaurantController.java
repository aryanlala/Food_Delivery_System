package com.example.demo.controller;

import com.example.demo.models.MenuItem;
import com.example.demo.models.Restaurant;
import com.example.demo.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    // Browse Restaurants
    @GetMapping("")
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        return ResponseEntity.ok(restaurants);
    }

    // Get Restaurant Menu
    @GetMapping("/{restaurantId}/menu")
    public ResponseEntity<List<MenuItem>> getRestaurantMenu(@PathVariable Long restaurantId) {
        List<MenuItem> menuItems = restaurantService.getMenuItemsByRestaurantId(restaurantId);
        return ResponseEntity.ok(menuItems);
    }

    // Search Menu Items
    @GetMapping("/menu/search")
    public ResponseEntity<List<MenuItem>> searchMenuItems(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String cuisine,
            @RequestParam(required = false) Boolean vegetarian) {

        List<MenuItem> menuItems = restaurantService.searchMenuItems(query, cuisine, vegetarian);
        return ResponseEntity.ok(menuItems);
    }
}

