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


# Modules 

- Admin Module  
- Category Module
- Product Module
- Customer Module
- Address Module
- Cart Module
- Orders Module


### Admin Features 

- Sign-Up Admin Handler (http://localhost:8888/adminController/adminSignUp)
- Log-in Admin Handler (http://localhost:8888/adminController/loginAdmin)
- Log-out Admin Handler (http://localhost:8888/adminController/logoutAdmin?key={KEY})
- Find All Admins Handler (http://localhost:8888/adminController/admins)
- Find Admin By Admin Id Handler (http://localhost:8888/adminController/adminsByAdminId?adminId={adminId})
- Find Admin By User-Name Handler (http://localhost:8888/adminController/adminsByUserName?adminUserName={adminUserName})
- Update Admin Details Handler (http://localhost:8888/adminController/updateAdmin?key={KEY})
- Delete Admin Handler (http://localhost:8888/adminController/deleteAdmins?key={KEY})

![01](https://user-images.githubusercontent.com/103619788/220114469-601fbb07-598b-44ed-865d-d1becb749b9c.jpg)

### Category Features 

- Add Category Handler (http://localhost:8888/categoryController/addCategory?key={KEY})
- Find Category By Category Name Handler (http://localhost:8888/categoryController/categoryByCategoryName?categoryName={categoryName})
- Find All Categorys Handler(http://localhost:8888/categoryController/categorys)
- Update Category Details Handler(http://localhost:8888/categoryController/updateCategory?key={KEY})
- Delete Category Handler(http://localhost:8888/categoryController/deleteCategory?categoryName={categoryName}&key={KEY})

![03](https://user-images.githubusercontent.com/103619788/220283745-11c5ca3a-1398-469b-be67-07a713ef6d97.jpg)

### Product Features 

- Add Product Handler (http://localhost:8888/productController/addProduct?categoryName={categoryName}&key={KEY})
- Find Product By Category Name Handler(http://localhost:8888/productController/productByCategoryName?categoryName={categoryName})
- Find Product By Product Name Handler(http://localhost:8888/productController/productByProductName?productName={productName})
- Find All Product Handler(http://localhost:8888/productController/Products)
- Update Product Details Handler(http://localhost:8888/productController/updateProduct?key={KEY})
- Delete Product Handler(http://localhost:8888/productController/deleteProduct?productId={productId}&categoryName={categoryName}&key={KEY})

![04](https://user-images.githubusercontent.com/103619788/220287227-d7603a48-2d88-4c99-954c-0d8144457b03.jpg)

### Customer Features 

- Sign-Up Customer Handler (http://localhost:8888/customerController/customerSignUp)
- Log-in Customer Handler (http://localhost:8888/customerController/loginCustomer)
- Log-out Customer Handler (http://localhost:8888/customerController/logoutCustomer?key={key})
- Add Address Handler (http://localhost:8888/customerController/addAddress?key={key})
- Find Customer By Customer-Id Handler (http://localhost:8888/customerController/customerByCustomerId?customerId={customerId})
- Find Customer By User-Name Handler (http://localhost:8888/customerController/customerByUserName?customerUserName={customerUserName})
- Find All Customers Handler (http://localhost:8888/customerController/customers)
- Update Customer Details Handler (http://localhost:8888/customerController/updateCustomer?key={key})
- Delete Address Handler (http://localhost:8888/customerController/deleteAddress?addressId={addressId}&key={key})
- Delete Customer Handler (http://localhost:8888/customerController/deleteCustomer?key={key})

![02](https://user-images.githubusercontent.com/103619788/220293152-c4b2f41b-6318-403d-a5a1-edae4e490484.jpg)

### Cart Features 

- Add Product To Cart Handler (http://localhost:8888/cartController/addProductToCart?productName={productName}&quantity={quantity}&key={key})
- Update Product Quantity Handler (http://localhost:8888/cartController/updateProductQuantity?productName={productName}&quantity={quantity}&key={key})
- Find All Products In Cart Handler (http://localhost:8888/cartController/getAllProductsInCart?key={key})
- Remove Product From Cart Handler (http://localhost:8888/cartController/deleteProductFromCart?productName={productName}&key={key})
- Remove All Products From Cart Handler (http://localhost:8888/cartController/removeAllProductsFromCart?key={key})

![05](https://user-images.githubusercontent.com/103619788/220913053-7a8f920d-e450-487e-9083-8a72692189d7.jpg)

### Orders Features 

- Place Order Handler (http://localhost:8888/ordersController/placeOrder?addressId={addressId}&key={key})
- Cancel Order Handler (http://localhost:8888/ordersController/cancelOrder?orderId={orderId}&key={key})
- Find All Orders Handler (http://localhost:8888/ordersController/getAllOrders?key={key})
- Find All Orders of Customer Handler (http://localhost:8888/ordersController/getAllOrdersByCustomerID?key={key})
- Update Delivery Address Handler (http://localhost:8888/ordersController/UpdateDeliveryAddress?orderId={orderId}&addressId={addressId}&key={key})
- Update Delivery Status Handler (http://localhost:8888/ordersController/UpdateDeliveryStatus?orderId={orderId}&status={deliveryStatus}&key={key})
- Update Delivery Date Handler (http://localhost:8888/ordersController/UpdateDeliveryDate?date={deliveryDate}&orderId={orderId}&key={key})
- Update Delivery Status By Order Date Handler (http://localhost:8888/ordersController/UpdateDeliveryStatusByOrderdate?date={orderDate}&status={deliveryStatus}&key={key})
- Cancel Orders By Order Date Handler (http://localhost:8888/ordersController/cancelOrdersByOrderDate?date={orderDate}&key={key})

![07](https://user-images.githubusercontent.com/103619788/221241114-fcd8303b-8cab-4312-852f-33f00bc2a318.jpg)

# Technology and Tools used 

- Java
- MySQL
- Spring Boot
- Spring Data JPA
- Hibernate
- lombok
- Swagger
- Maven
- Git & GitHub
- Spring tool suite



# Entity Relationship Diagram (ERD)
![123](https://user-images.githubusercontent.com/103619788/219714882-03e5f097-63a4-4ab8-9d8b-ce2842e6eff2.jpg)

## Installation & Run

* Before running the API server, you should update the database config inside the [application.properties]([https://github.com/avanishmani/Online_Marketplace_API/edit/main/resources/application.properties) file. 
* Update the port number, username and password as per your local database config.

```
    server.port=8888
    spring.datasource.url=jdbc:mysql://localhost:3306/saveright;
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.datasource.username=root
    spring.datasource.password=root
```


# Features (http://localhost:8888/swagger-ui/#/)


## Author

- [Avanish Mani Tripathi](https://github.com/avanishmani)
