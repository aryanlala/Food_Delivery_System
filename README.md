# Food Delivery System

- Backend APIs for a food delivery platform. The platform has different users, including customers, restaurant owners, delivery personnel, and administrators, each with specific actions they can perform.
- The API allows customers to place orders, restaurants to manage menus, delivery personnel to handle deliveries, and administrators to oversee the system.

## Functional Requirements

1. Customer Functionalities
2. Restaurant Owner Functionalities
3. Delivery Personnel Functionalities
4. Administrator Functionalities

## Authentication Steps

1. Register using `/api/auth/register` endpoint:
   - Provide email, name, password
   - Specify role as one of:
     - CUSTOMER
     - RESTAURANT_OWNER
     - DELIVERY_PERSONNEL
     - ADMIN

2. Login using `/api/auth/login` endpoint with registered email and password
3. Use the generated JWT token:
   - Copy the token from the response
   - Paste it in the Authorize section of Swagger UI
   - Format: `Bearer <your_token>`

## Technology Stack

- Backend: Spring Boot
- Database: SQL database hosted on Aiven
- Authentication: JWT Bearer Tokens
- Documentation: Swagger UI
