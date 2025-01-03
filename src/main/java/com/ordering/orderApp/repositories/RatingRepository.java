package com.ordering.orderApp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ordering.orderApp.entities.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {
	Page<Rating> findByRestaurantId(long restaurantId, Pageable pageable);
}
