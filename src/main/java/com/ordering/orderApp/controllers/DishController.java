package com.ordering.orderApp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ordering.orderApp.payload.ApiResponse;
import com.ordering.orderApp.payload.DishDto;
import com.ordering.orderApp.payload.ResponsePaginationObject;
import com.ordering.orderApp.services.DishService;
import com.ordering.orderApp.utilis.Constants.ApplicationConstants;

@RestController
@RequestMapping("/api/")
public class DishController {
	private DishService dishService;

	public DishController(DishService dishService) {
		super();
		this.dishService = dishService;
	}

	@DeleteMapping("/restaurants/{restaurantId}/dishes/{dishId}")
	public ResponseEntity<ApiResponse<DishDto>> deleteDishById(@PathVariable(value = "restaurantId") long restaurantId,
			@PathVariable(value = "dishId") long dishId) {
		dishService.deleteDishById(restaurantId, dishId);
		ApiResponse<DishDto> res = new ApiResponse<>();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PutMapping("/restaurants/{restaurantId}/dishes/{dishId}")
	public ResponseEntity<ApiResponse<DishDto>> updateDish(@PathVariable(value = "restaurantId") long restaurantId,
			@PathVariable(value = "dishId") long dishId, @RequestBody DishDto reqBody) {
		DishDto updated = dishService.updateDish(restaurantId, dishId, reqBody);
		ApiResponse<DishDto> res = new ApiResponse<>(updated);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("/restaurants/{restaurantId}/dishes/{dishId}")
	public ResponseEntity<ApiResponse<DishDto>> getDishById(@PathVariable(value = "restaurantId") long restaurantId,
			@PathVariable(value = "dishId") long dishId) {
		DishDto found = dishService.getDishById(restaurantId, dishId);
		ApiResponse<DishDto> res = new ApiResponse<>(found);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PostMapping("/restaurants/{restaurantId}/dishes")
	public ResponseEntity<ApiResponse<DishDto>> createDish(@RequestBody DishDto toCreate,
			@PathVariable(value = "restaurantId") long restaurantId) {
		DishDto created = dishService.createDish(restaurantId, toCreate);
		ApiResponse<DishDto> res = new ApiResponse<>(created);
		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}

	@GetMapping("/restaurants/{restaurantId}/dishes")
	public ResponseEntity<ApiResponse<ResponsePaginationObject<DishDto>>> getDishesByRestaurantId(
			@PathVariable(value = "restaurantId") long restaurantId,
			@RequestParam(value = "pageNo", defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = ApplicationConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = ApplicationConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
		ResponsePaginationObject<DishDto> resObj = dishService.getDishesByRestaurantId(restaurantId, pageNo, pageSize,
				sortBy, sortDir);
		ApiResponse<ResponsePaginationObject<DishDto>> res = new ApiResponse<>(resObj);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

}
