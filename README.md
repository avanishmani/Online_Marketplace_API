# Online_Marketplace_API
developing a backend API for an online marketplace using Java Spring Boot. The API should allow users to perform various operations related to products and orders


# Problem Statement.

This API allows users to perform CRUD operations on Product entities and place and view orders for specific products.

## Requirements

- Create a Product entity with the following attributes:
  - ID (auto-generated)
  - Name
  - Description
  - Price
  - Quantity
- Implement CRUD (Create, Read, Update, Delete) operations for the Product entity using RESTful endpoints.
- Create an Order entity with the following attributes:
  - ID (auto-generated)
  - User ID (the ID of the user who placed the order)
  - Product ID (the ID of the product being ordered)
  - Quantity
- Implement the following operations for the Order entity using RESTful endpoints:
  - Place an order
  - Get all orders for a specific user
  - Get all orders for a specific product
- Implement validation and error handling for the API endpoints. For example, handle cases where a product is out of stock or a user tries to place an order with an invalid quantity.
