package com.example.demo.controller;

import com.example.demo.dto.MenuRequest;
import com.example.demo.dto.RestaurantAccountDTO;
import com.example.demo.exception.GenericException;
import com.example.demo.models.MenuItem;
import com.example.demo.models.Order;
import com.example.demo.models.Restaurant;
import com.example.demo.security.UserLoginService;
import com.example.demo.service.MenuService;
import com.example.demo.service.RestaurantMenuService;
import com.example.demo.service.RestaurantProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/owner")
@Slf4j
public class RestaurantOwnerController {
    @Autowired
    private RestaurantProfileService restaurantProfileService;

    @Autowired
    private RestaurantMenuService restaurantMenuService;

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private MenuService menuService;

    @PostMapping("/account/create-account")
    public ResponseEntity<HttpStatus> createAccount(@RequestBody RestaurantAccountDTO restaurantAccountDTO) throws GenericException {
        String loggedInUserId = userLoginService.getLoggedInUserEmail();

        if(loggedInUserId == null) {
            throw new GenericException("No logged in user found ..");
        }

        restaurantProfileService.createRestaurantProfile(loggedInUserId, restaurantAccountDTO.restaurantName(),
                restaurantAccountDTO.address(), restaurantAccountDTO.phoneNumber());
        log.info("Restaurant account created successfully..");
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/account/manage-account")
    public ResponseEntity<HttpStatus> updateAccount(
            @RequestBody RestaurantAccountDTO restaurantAccountDTO)
            throws GenericException {
        String loggedInUserId = userLoginService.getLoggedInUserEmail();

        if(loggedInUserId == null) {
            throw new GenericException("No logged in user found ..");
        }

        restaurantProfileService.updateRestaurantProfile(loggedInUserId, restaurantAccountDTO.restaurantName(),
                restaurantAccountDTO.address(), restaurantAccountDTO.phoneNumber());
        log.info("Restaurant account updated successfully..");
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/account/profile")
    public ResponseEntity<List<Restaurant>> getRestaurantProfileByUserId() {
        String loggedInUserId = userLoginService.getLoggedInUserEmail();
        List<Restaurant> restaurantProfiles = restaurantProfileService.getRestaurantProfileByUserId(loggedInUserId);
        return ResponseEntity.ok(restaurantProfiles);
    }

    @PostMapping("{restaurant-account-id}/menu/add-menu")
    public ResponseEntity<List<MenuItem>> addMenu(@PathVariable(name = "restaurant-account-id") Long restaurantId,
                                                  @RequestBody List<MenuRequest> menuItemRequests, @PathVariable("restaurant-account-id") String parameter) {
        List<MenuItem> menuItems = menuItemRequests.stream().map(menuRequest ->
                        MenuItem.builder()
                                .name(menuRequest.getName())
                                .price(menuRequest.getPrice())
                                .description(menuRequest.getDescription())
                                .vegetarian(menuRequest.getVegetarian())
                                .build())
                .collect(Collectors.toList());
        List<MenuItem> updatedMenuItems = restaurantProfileService.addMenuItems(restaurantId, menuItems);
        return ResponseEntity.ok(updatedMenuItems);
    }

    @PutMapping("{restaurant-account-id}/menu/{menu-item-name}/update-menu")
    public ResponseEntity<MenuItem> updateMenu(
            @PathVariable(name = "menu-item-name") String menuItemName,
            @PathVariable(name = "restaurant-account-id") Long restaurantId,
            @RequestBody MenuRequest menuRequest
    ) {
        MenuItem menuItem = MenuItem.builder()
                .name(menuRequest.getName())
                .price(menuRequest.getPrice())
                .description(menuRequest.getDescription())
                .vegetarian(menuRequest.getVegetarian())
                .build();
        MenuItem updatedMenuItem = menuService.updateMenuItem(menuItemName,restaurantId, menuItem.getDescription(),
                menuItem.getPrice(), menuItem.getVegetarian());
        return ResponseEntity.ok(updatedMenuItem);
    }

    @DeleteMapping("{restaurant-account-id}/menu/{menu-item-name}remove-menu")
    public ResponseEntity<HttpStatus> removeMenu(
            @PathVariable(name = "menu-item-name") String menuItemName,
            @PathVariable(name = "restaurant-account-id") Long restaurantId
    ) {
        menuService.removeMenuFromRestaurant(restaurantId, menuItemName);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("{restaurant-account-id}/orders")
    public ResponseEntity<List<Order>> getAllOrdersByRestaurantId(@PathVariable(name = "restaurant-account-id") Long restaurantId) {
        List<Order> orders = restaurantMenuService.getOrdersByRestaurantId(restaurantId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("{restaurant-account-id}/orders/{status}")
    public ResponseEntity<List<Order>> getAllOrdersByRestaurantAccountIdAndStatus(
            @PathVariable(name = "restaurant-account-id") Long restaurantId,
            @PathVariable(name = "status") String status) {
        List<Order> orders = restaurantMenuService.getOrdersByRestaurantIdAndStatus(restaurantId, status);
        return ResponseEntity.ok(orders);
    }


    @PutMapping("{restaurant-account-id}/orders/{order-id}/update-status/{status}")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable(name = "restaurant-account-id") Long restaurantId,
            @PathVariable(name = "order-id") Long orderId,
            @PathVariable(name = "status") String status) {
        Order order = restaurantMenuService.updateOrder(restaurantId, orderId, status);
        return ResponseEntity.ok(order);
    }


}
