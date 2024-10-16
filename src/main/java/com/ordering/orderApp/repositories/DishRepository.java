package com.ordering.orderApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ordering.orderApp.entities.Dish;

public interface DishRepository extends JpaRepository<Dish, Long> {
	boolean existsByName(String name);
}
