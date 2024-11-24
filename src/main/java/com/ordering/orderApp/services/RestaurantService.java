package com.ordering.orderApp.services;

import com.ordering.orderApp.payload.ResponsePaginationObject;
import com.ordering.orderApp.payload.RestaurantDto;


public interface RestaurantService {
	RestaurantDto createRestaurant(RestaurantDto toCreate);

	RestaurantDto updateRestaurant(long restaurantId, RestaurantDto updated);

	ResponsePaginationObject<RestaurantDto> getRestaurants(int pageNo, int pageSize, String sortBy, String sortDir);

	RestaurantDto getRestaurantById(long restaurantId);

	void deleteRestaurantById(long restautantId);
}
