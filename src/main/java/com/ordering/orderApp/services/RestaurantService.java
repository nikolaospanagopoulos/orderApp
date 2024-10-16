package com.ordering.orderApp.services;

import com.ordering.orderApp.payload.RestaurantDto;

public interface RestaurantService {
	RestaurantDto createRestaurant(RestaurantDto toCreate);
}
