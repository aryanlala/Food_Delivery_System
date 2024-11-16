package com.example.demo.security;

import com.example.demo.service.CustomerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserLoginService {

    private final CustomerService customerService;

    public UserLoginService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public String getLoggedInUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername();
            } else {
                return principal.toString();
            }
        }
        return null;
    }

    public long getLoggedInUserId() {
        String username = getLoggedInUserEmail();
        return Optional.ofNullable(customerService.getCustomerByEmail(username).getId()).orElse(null);
    }
}
