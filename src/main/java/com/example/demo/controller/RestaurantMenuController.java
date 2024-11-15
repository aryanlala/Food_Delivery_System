package com.example.demo.controller;

import com.example.demo.models.MenuItem;
import com.example.demo.models.Restaurant;
import com.example.demo.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@Slf4j
public class RestaurantMenuController {

    @Autowired
    private MenuService menuService;

    // Browse Restaurants
    @GetMapping("")
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurants = menuService.getAllRestaurants();
        return ResponseEntity.ok(restaurants);
    }

    // Get Restaurant Menu
    @GetMapping("/{restaurantId}/menu")
    public ResponseEntity<List<MenuItem>> getRestaurantMenu(@PathVariable Long restaurantId) {
        List<MenuItem> menuItems = menuService.getMenuItemsByRestaurantId(restaurantId);
        return ResponseEntity.ok(menuItems);
    }

    // Search Menu Items
    @GetMapping("/menu/search")
    public ResponseEntity<List<MenuItem>> searchMenuItems(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String cuisine,
            @RequestParam(required = false) Boolean vegetarian) {

        List<MenuItem> menuItems = menuService.searchMenuItems(query, cuisine, vegetarian);
        return ResponseEntity.ok(menuItems);
    }
}

