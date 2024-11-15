package com.example.demo.service;

import com.example.demo.models.*;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PaymentDetailsRepository paymentDetailsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "email", email));
    }

    public Address addAddress(String email, Address address) {
        Customer customer = getCustomerByEmail(email);
        address.setCustomer(customer);
        return addressRepository.save(address);
    }

    public PaymentDetails addPaymentDetails(String email, PaymentDetails paymentDetails) {
        Customer customer = getCustomerByEmail(email);
        paymentDetails.setCustomer(customer);
        return paymentDetailsRepository.save(paymentDetails);
    }

}