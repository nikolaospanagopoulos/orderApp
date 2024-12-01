package com.ordering.orderApp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ordering.orderApp.entities.Dish;

public interface DishRepository extends JpaRepository<Dish, Long> {
	boolean existsByNameAndRestaurantId(String name, long restaurantId);

	Page<Dish> findByRestaurantId(long restaurantId, Pageable pageable);
}
