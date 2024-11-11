package com.ordering.orderApp.services;

import com.ordering.orderApp.payload.RatingDto;

public interface RatingService {
	RatingDto createRating(long restaurantId, RatingDto toRate);

	RatingDto getRatingById(long restaurantId, long ratingId);
}
