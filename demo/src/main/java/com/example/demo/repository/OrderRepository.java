package com.example.demo.repository;

import com.example.demo.models.Order;
import com.example.demo.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByRestaurantIdAndId(Long restaurantId, Long id);
    List<Order> findByCustomer(Customer customer);

    List<Order> findByRestaurantIdAndStatus(Long restaurantId, String status);
    List<Order> findByRestaurantId(Long restaurantId);


}