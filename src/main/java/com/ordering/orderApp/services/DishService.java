package com.ordering.orderApp.services;

import com.ordering.orderApp.payload.DishDto;

public interface DishService {
	DishDto createDish(long restaurantId, DishDto toCreate);
}
