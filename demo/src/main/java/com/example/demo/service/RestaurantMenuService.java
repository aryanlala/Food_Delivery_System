package com.example.demo.service;

import com.example.demo.models.Order;
import com.example.demo.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantMenuService {

    private OrderRepository orderRepository;

    public RestaurantMenuService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getOrdersByRestaurantId(Long restaurantId) {
        return orderRepository.findByRestaurantId(restaurantId);
    }

    public List<Order> getOrdersByRestaurantIdAndStatus(Long restaurantId, String status) {
        return orderRepository.findByRestaurantIdAndStatus(restaurantId, status);
    }

    public Order updateOrder(Long restaurantId, Long orderId,String status) {
        Order order = orderRepository.findByRestaurantIdAndId(restaurantId, orderId);
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
