package com.example.demo.service;

import com.example.demo.models.MenuItem;
import com.example.demo.models.Restaurant;
import com.example.demo.repository.MenuItemRepository;
import com.example.demo.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public List<MenuItem> getMenuItemsByRestaurantId(Long restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId);
    }

    public List<MenuItem> searchMenuItems(String query, String cuisine, Boolean vegetarian) {
        if (query == null) query = "";
        if (cuisine == null) cuisine = "";
        if (vegetarian == null) vegetarian = false;

        return menuItemRepository.findByNameContainingIgnoreCaseAndRestaurant_CuisineTypeContainingIgnoreCaseAndVegetarian(
                query, cuisine, vegetarian);
    }

    public MenuItem updateMenuItem(String name,Long restaurantId, String description, Double price, Boolean vegetarian) {
        MenuItem menuItem = Optional.ofNullable(menuItemRepository.findByNameAndRestaurantId(name, restaurantId))
                .orElseThrow(() -> new IllegalArgumentException("MenuItem not found"));

        if (description != null) {
            menuItem.setDescription(description);
        }
        if (price != null) {
            menuItem.setPrice(price);
        }
        if (vegetarian != null) {
            menuItem.setVegetarian(vegetarian);
        }

        return menuItemRepository.save(menuItem);
    }

    public void removeMenuFromRestaurant(Long restaurantId, String menuItemName) {
        MenuItem menuItem = Optional.ofNullable(menuItemRepository.findByNameAndRestaurantId(menuItemName, restaurantId))
                .orElseThrow(() -> new IllegalArgumentException("MenuItem not found"));

        menuItemRepository.delete(menuItem);
    }

}
