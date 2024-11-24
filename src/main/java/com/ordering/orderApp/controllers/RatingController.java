package com.ordering.orderApp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ordering.orderApp.payload.ApiResponse;
import com.ordering.orderApp.payload.RatingDto;
import com.ordering.orderApp.payload.ResponsePaginationObject;
import com.ordering.orderApp.services.RatingService;
import com.ordering.orderApp.utilis.Constants.ApplicationConstants;

@RestController
@RequestMapping("/api/")
public class RatingController {

	RatingService ratingService;

	public RatingController(RatingService ratingService) {
		super();
		this.ratingService = ratingService;
	}

	@GetMapping("/restaurants/{restaurantId}/ratings/{ratingId}")
	public ResponseEntity<ApiResponse<RatingDto>> getRatingById(@PathVariable(value = "restaurantId") long restaurantId,
			@PathVariable(value = "ratingId") long ratingId) {
		ApiResponse<RatingDto> res = new ApiResponse<>(ratingService.getRatingById(restaurantId, ratingId));
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("/restaurants/{restaurantId}/ratings")
	public ResponseEntity<ApiResponse<ResponsePaginationObject<RatingDto>>> getRatingsByRestaurantId(
			@PathVariable(value = "restaurantId") long restaurantId,
			@RequestParam(value = "pageNo", defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = ApplicationConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = ApplicationConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {

		ResponsePaginationObject<RatingDto> resObj = ratingService.getRatingsByRestaurantId(restaurantId, pageNo,
				pageSize, sortBy, sortDir);
		ApiResponse<ResponsePaginationObject<RatingDto>> res = new ApiResponse<>(resObj);
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@PostMapping("/restaurants/{restaurantId}/ratings")
	public ResponseEntity<ApiResponse<RatingDto>> createRating(@RequestBody RatingDto newRating,
			@PathVariable(value = "restaurantId") long restaurantId) {

		RatingDto created = ratingService.createRating(restaurantId, newRating);
		ApiResponse<RatingDto> res = new ApiResponse<>(created);
		return new ResponseEntity<>(res, HttpStatus.CREATED);

	}
}
