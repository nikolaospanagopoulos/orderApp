package com.ordering.orderApp.services;

import com.ordering.orderApp.payload.OrderDto;
import com.ordering.orderApp.payload.OrderResponseDto;

public interface OrderService {
	OrderResponseDto createOrder(long restaurantId, OrderDto requestBody);
}
