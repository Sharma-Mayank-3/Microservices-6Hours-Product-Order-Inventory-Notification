package com.api.order.service;

import com.api.order.dto.OrderRequestDto;
import com.api.order.dto.OrderResponseDto;

public interface OrderService {

    String createOrder(OrderRequestDto orderRequestDto);

    OrderResponseDto getOrderById(int orderId);

}
