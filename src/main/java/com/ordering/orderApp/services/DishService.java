package com.ordering.orderApp.services;

import com.ordering.orderApp.payload.DishDto;
import com.ordering.orderApp.payload.ResponsePaginationObject;

public interface DishService {
	DishDto createDish(long restaurantId, DishDto toCreate);

	DishDto getDishById(long restaurantId, long dishId);

	DishDto updateDish(long restaurantId, long dishId, DishDto reqBody);

	ResponsePaginationObject<DishDto> getDishesByRestaurantId(long restaurantId, int pageNo, int pageSize, String sortBy,
			String sortDir);

	void deleteDishById(long restaurantId, long dishId);
}
