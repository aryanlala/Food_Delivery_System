package com.example.demo.service;

import com.example.demo.models.MenuItem;
import com.example.demo.models.Restaurant;
import com.example.demo.models.RestaurantOwner;
import com.example.demo.repository.MenuItemRepository;
import com.example.demo.repository.RestaurantOwnerRepository;
import com.example.demo.repository.RestaurantRepository;
import com.example.demo.security.Role;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.random.RandomGenerator;

@Service
public class RestaurantProfileService {

    private final RestaurantRepository restaurantRepository;

    private final MenuItemRepository menuItemRepository;

    private final RestaurantOwnerRepository restaurantOwnerRepository;

    public RestaurantProfileService(RestaurantRepository restaurantRepository, MenuItemRepository menuItemRepository, RestaurantOwnerRepository restaurantOwnerRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
        this.restaurantOwnerRepository = restaurantOwnerRepository;
    }

    @Transactional
    public Restaurant createRestaurantProfile(String email, String restaurantName,
                                              String address,
                                              String phoneNumber) {
        Optional<RestaurantOwner> userOpt = restaurantOwnerRepository.findByEmail(email);
        if (userOpt.isEmpty() || !Role.RESTAURANT_OWNER.equals(userOpt.get().getRole())) {
            throw new IllegalArgumentException("User must be a restaurant owner to create a restaurant profile.");
        }

        Restaurant profile = Restaurant.builder()
                .restaurantOwner(userOpt.get())
                .name(restaurantName)
                .address(address)
                .phoneNumber(phoneNumber)
                .id(RandomGenerator.getDefault().nextLong())
                .build();

        return restaurantRepository.save(profile);
    }

    @Transactional
    public Restaurant updateRestaurantProfile(String email, String restaurantName,
                                                     String address,
                                                     String phoneNumber) {
        Restaurant profile = Optional.ofNullable(restaurantRepository.findByRestaurantOwnerEmailAndName(email, restaurantName))
                .orElseThrow(() -> new IllegalArgumentException("RestaurantProfile not found"));
        if(!email.equals(profile.getRestaurantOwner().getEmail())) {
            throw new IllegalArgumentException("Only owner can update details about restaurant");
        }
        if(!Strings.isEmpty(restaurantName)) {
            profile.setName(restaurantName);
        }

        if(!Strings.isEmpty(address)) {
            profile.setAddress(address);
        }

        if(!Strings.isEmpty(phoneNumber)) {
            profile.setPhoneNumber(phoneNumber);
        }
        return restaurantRepository.save(profile);
    }

    public List<Restaurant> getRestaurantProfileByUserId(String email) {
        return restaurantRepository.findByRestaurantOwnerEmail(email);
    }

    public List<MenuItem> addMenuItems(Long restaurantId, List<MenuItem> menuItems) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));
        menuItems.forEach(menuItem -> menuItem.setRestaurant(restaurant));
        return menuItemRepository.saveAll(menuItems);
    }
}
