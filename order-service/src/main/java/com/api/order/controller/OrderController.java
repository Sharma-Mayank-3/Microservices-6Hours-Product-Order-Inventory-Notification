package com.api.order.controller;

import com.api.order.dto.OrderRequestDto;
import com.api.order.dto.OrderResponseDto;
import com.api.order.payload.ApiResponse;
import com.api.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse> createOrder(@RequestBody OrderRequestDto orderRequestDto){
        String order = this.orderService.createOrder(orderRequestDto);
        ApiResponse orderStatus = ApiResponse.builder()
                .data(order)
                .message("order-status")
                .status(true)
                .serviceName("order-service")
                .build();
        return new ResponseEntity<>(orderStatus, HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable("orderId") int orderId){
        OrderResponseDto orderById = this.orderService.getOrderById(orderId);
        ApiResponse orderStatus = ApiResponse.builder()
                .data(orderById)
                .message("order-byId")
                .status(true)
                .serviceName("order-service")
                .build();
        return new ResponseEntity<>(orderStatus, HttpStatus.OK);
    }

}
