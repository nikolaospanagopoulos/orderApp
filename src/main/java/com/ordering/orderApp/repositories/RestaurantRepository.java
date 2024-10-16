package com.ordering.orderApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ordering.orderApp.entities.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
	boolean existsByName(String name);
}
