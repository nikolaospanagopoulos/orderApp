package com.ordering.orderApp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ordering.orderApp.payload.RestaurantDto;
import com.ordering.orderApp.services.RestaurantService;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
	private RestaurantService restaurantService;

	public RestaurantController(RestaurantService restaurantService) {
		super();
		this.restaurantService = restaurantService;
	}

	@PostMapping
	public ResponseEntity<RestaurantDto> createRestaurant(@RequestBody RestaurantDto toCreate) {
		RestaurantDto created = restaurantService.createRestaurant(toCreate);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}
}
