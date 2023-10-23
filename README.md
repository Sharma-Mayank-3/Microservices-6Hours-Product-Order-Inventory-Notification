# Microservices-6Hours-Product-Order-Inventory-Notification

# In This Project 4 microservices will be created.

# Product-Service, Order-Service, Inventory-Service.

# Product-Service 
```properties
this will have productId, ProductName and productPrice.
```

# Order-Service
```properties
this will have orderId, List<productId>, orderPrice. 
to get the product price this will make a synchronous \
  call to product service and pass the list of product in order.
```

# Inventory-Service
```properties
This will have inventoryId, ProductId, stock Info.

Again Order-service will make a synchronous call to \
  inventory service to get the staock info while placing the order
```

# All Synchronous Calls
```properties
1. while placing the order to order-service a synchronous call will go to product service
to get the  productPrice info, Also It will make a synchronous call to inventory service to get
the stock information. Both will be a parallel call, If both looks good then a order will be created.

2. Calculate the overAll price of order and call the Inventory-Service Synchrous call to get  
the inventory informantion.

3. If all the products in the order are available then place the order else not place.  
```

# NOTE 
```properties
1. to fetch the product information from list of product, we need to make a post call rather than
get call, since get will work in postman but post will work with feign client.
Also Standard practice is to make a postcall if we are sending a request body.
```