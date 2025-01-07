package com.ordering.orderApp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ordering.orderApp.payload.ApiResponse;
import com.ordering.orderApp.payload.OrderDto;
import com.ordering.orderApp.payload.OrderResponseDto;
import com.ordering.orderApp.services.OrderService;

@RestController
@RequestMapping("/api/")
public class OrderController {

	private OrderService orderService;

	public OrderController(OrderService orderService) {
		super();
		this.orderService = orderService;
	}

	// create order for everyone
	@PreAuthorize("hasRole('ADMIN') or hasRole('OWNER') or hasRole('USER')")
	@PostMapping("/restaurants/{restaurantId}/orders")
	public ResponseEntity<ApiResponse<OrderResponseDto>> createOrder(
			@PathVariable(value = "restaurantId") long restaurantId, @RequestBody OrderDto requestBody) {
		OrderResponseDto orderResponse = orderService.createOrder(restaurantId, requestBody);
		ApiResponse<OrderResponseDto> res = new ApiResponse<>(orderResponse);
		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}

}
