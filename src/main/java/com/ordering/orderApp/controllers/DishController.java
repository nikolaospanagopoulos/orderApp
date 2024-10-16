package com.ordering.orderApp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ordering.orderApp.payload.DishDto;
import com.ordering.orderApp.services.DishService;

@RestController
@RequestMapping("/api/")
public class DishController {
	private DishService dishService;

	public DishController(DishService dishService) {
		super();
		this.dishService = dishService;
	}

	@PostMapping("/restaurants/{restaurantId}/dishes")

	public ResponseEntity<DishDto> createDish(@RequestBody DishDto toCreate,
			@PathVariable(value = "restaurantId") long restaurantId) {
		DishDto created = dishService.createDish(restaurantId, toCreate);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}
}
