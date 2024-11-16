package com.example.demo.security;


import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthenticationEntryPoint unauthorizedHandler;
    private final JwtTokenUtil jwtTokenUtil;

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService,
                             JwtAuthenticationEntryPoint unauthorizedHandler,
                             JwtTokenUtil jwtTokenUtil) {
        this.userDetailsService = userDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenUtil, userDetailsService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(unauthorizedHandler)
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/isActive").permitAll()
                        .requestMatchers("/buildInfo").permitAll()
                        .requestMatchers("/openapi").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers("/profile").permitAll()
                        .requestMatchers("/v3/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/swagger-ui.html").permitAll()
                        .requestMatchers("/openapi/**").permitAll()
                        // Public endpoints
                        .requestMatchers("/api/auth/**", "/api/restaurants/**").permitAll()

                        // Admin-only access
                        .requestMatchers("/api/admin/**").hasAuthority("ADMINISTRATOR")

                        // Restaurant Owner access
                        .requestMatchers("/api/owner/**").hasAuthority("RESTAURANT_OWNER")

                        // Delivery Personnel access
                        .requestMatchers("/api/delivery/**").hasAuthority("DELIVERY_PERSON")

                        // Customer access
                        .requestMatchers("/api/customers/**").hasAuthority("CUSTOMER")
                        .anyRequest().authenticated()

                );

        // Add JWT filter
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // AuthenticationManager bean
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // PasswordEncoder bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}