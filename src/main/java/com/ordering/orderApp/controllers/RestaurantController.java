package com.ordering.orderApp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.ordering.orderApp.payload.ResponsePaginationObject;
import com.ordering.orderApp.payload.RestaurantDto;
import com.ordering.orderApp.payload.entities.CustomUserDetails;
import com.ordering.orderApp.services.RestaurantService;
import com.ordering.orderApp.utilis.Constants.ApplicationConstants;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
	private RestaurantService restaurantService;

	public RestaurantController(RestaurantService restaurantService) {
		super();
		this.restaurantService = restaurantService;
	}

	@PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<RestaurantDto>> deleteRestaurant(@PathVariable(name = "id") long id) {
		
		restaurantService.deleteRestaurantById(id);
		ApiResponse<RestaurantDto> res = new ApiResponse<>();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<RestaurantDto>> updateRestaurant(@PathVariable(name = "id") long id,
			@RequestBody RestaurantDto reqBody) {
		ApiResponse<RestaurantDto> res = new ApiResponse<>(restaurantService.updateRestaurant(id, reqBody));
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<RestaurantDto>> getRestaurantById(@PathVariable(name = "id") long id) {
		ApiResponse<RestaurantDto> res = new ApiResponse<>(restaurantService.getRestaurantById(id));
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")

	@PostMapping
	public ResponseEntity<ApiResponse<RestaurantDto>> createRestaurant(@RequestBody RestaurantDto toCreate) {
		RestaurantDto created = restaurantService.createRestaurant(toCreate);
		ApiResponse<RestaurantDto> res = new ApiResponse<RestaurantDto>(created);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		String loggedInUsername = userDetails.getUsername();

		// You can now use loggedInUsername in your business logic
		System.out.println("Logged-in user: " + loggedInUsername + " " + userDetails.getEmail());
		userDetails.getAuthorities().stream().forEach(a -> System.out.println(a.getAuthority()));
		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<ApiResponse<ResponsePaginationObject<RestaurantDto>>> getRestaurants(
			@RequestParam(value = "pageNo", defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = ApplicationConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = ApplicationConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
		ApiResponse<ResponsePaginationObject<RestaurantDto>> res = new ApiResponse<>(
				restaurantService.getRestaurants(pageNo, pageSize, sortBy, sortDir));
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
