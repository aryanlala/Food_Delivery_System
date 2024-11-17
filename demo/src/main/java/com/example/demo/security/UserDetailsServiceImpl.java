package com.example.demo.security;

import com.example.demo.models.Customer;
import com.example.demo.models.RestaurantOwner;
import com.example.demo.models.DeliveryPersonnel;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.RestaurantOwnerRepository;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;
import com.example.demo.repository.DeliveryPersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private CustomerRepository customerRepository;
    private DeliveryPersonnelRepository deliveryPersonnelRepository;

    private RestaurantOwnerRepository restaurantOwnerRepository;

    public UserDetailsServiceImpl(CustomerRepository customerRepository, RestaurantOwnerRepository restaurantOwnerRepository, DeliveryPersonnelRepository deliveryPersonnelRepository) {
        this.customerRepository = customerRepository;
        this.restaurantOwnerRepository = restaurantOwnerRepository;
        this.deliveryPersonnelRepository = deliveryPersonnelRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Attempt to retrieve the user from each specific repository
        if (customerRepository.existsByEmail(email)) {
            Customer customer = customerRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Customer not found with email: " + email));
            return createUserDetails(customer.getEmail(), customer.getPassword(), customer.getRole());
        } else if (deliveryPersonnelRepository.existsByEmail(email)) {
            DeliveryPersonnel deliveryPersonnel = deliveryPersonnelRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Delivery Person not found with email: " + email));
            return createUserDetails(deliveryPersonnel.getEmail(), deliveryPersonnel.getPassword(), deliveryPersonnel.getRole());
        } else if (restaurantOwnerRepository.existsByEmail(email)) {
            RestaurantOwner owner = restaurantOwnerRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Restaurant Owner not found with email: " + email));
            return createUserDetails(owner.getEmail(), owner.getPassword(), owner.getRole());
        }
//         else if (administratorRepository.existsByEmail(email)) {
//            Administrator admin = administratorRepository.findByEmail(email)
//                    .orElseThrow(() -> new UsernameNotFoundException("Administrator not found with email: " + email));
//            return createUserDetails(admin.getEmail(), admin.getPassword(), admin.getRole());
//        }
//
        else {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }

    // Helper method to create UserDetails with granted authorities based on role
    private UserDetails createUserDetails(String email, String password, Role role) {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role.name()));
        return new org.springframework.security.core.userdetails.User(email, password, authorities);
    }
}