package com.ordering.orderApp.services;

import com.ordering.orderApp.payload.RestaurantDto;
import com.ordering.orderApp.payload.RestaurantResponsePaginationObject;

public interface RestaurantService {
	RestaurantDto createRestaurant(RestaurantDto toCreate);

	RestaurantDto updateRestaurant(long restaurantId, RestaurantDto updated);

	RestaurantResponsePaginationObject getRestaurants(int pageNo, int pageSize, String sortBy, String sortDir);

	RestaurantDto getRestaurantById(long restaurantId);

	void deleteRestaurantById(long restautantId);
}
