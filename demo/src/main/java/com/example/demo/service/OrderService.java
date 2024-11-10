package com.example.demo.service;

import com.example.demo.dto.OrderRequest;
import com.example.demo.dto.OrderItemRequest;
import com.example.demo.models.*;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PaymentDetailsRepository paymentDetailsRepository;

    public Order placeOrder(OrderRequest orderRequest, String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "email", email));

        Restaurant restaurant = restaurantRepository.findById(orderRequest.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant", "id", orderRequest.getRestaurantId()));

        Address address = addressRepository.findById(orderRequest.getAddressId())
                .orElseThrow(() -> new ResourceNotFoundException("Address", "id", orderRequest.getAddressId()));

        PaymentDetails paymentDetails = paymentDetailsRepository.findById(orderRequest.getPaymentDetailsId())
                .orElseThrow(() -> new ResourceNotFoundException("PaymentDetails", "id", orderRequest.getPaymentDetailsId()));

        Order order = new Order();
        order.setCustomer(customer);
        order.setRestaurant(restaurant);
        order.setDeliveryAddress(address);
        order.setPaymentDetails(paymentDetails);
        order.setStatus("Preparing");
        order.setOrderTime(LocalDateTime.now());

        List<OrderItem> orderItems = new ArrayList<>();
        double totalPrice = 0;

        for (OrderItemRequest itemRequest : orderRequest.getItems()) {
            MenuItem menuItem = menuItemRepository.findById(itemRequest.getMenuItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("MenuItem", "id", itemRequest.getMenuItemId()));

            OrderItem orderItem = new OrderItem();
            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setOrder(order);

            totalPrice += menuItem.getPrice() * itemRequest.getQuantity();
            orderItems.add(orderItem);
        }

        order.setTotalPrice(totalPrice);
        order.setOrderItems(orderItems);

        return orderRepository.save(order);
    }

    public List<Order> getOrderHistory(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "email", email));
        return orderRepository.findByCustomer(customer);
    }

    public String getOrderStatusByOrderId(Long orderId, String email){
            Customer customer = customerRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer", "email", email));
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new ResourceNotFoundException("Orders","Order Id",orderId));
            return order.getStatus();

    }

}