package com.ordering.orderApp.services;

import com.ordering.orderApp.payload.RatingDto;
import com.ordering.orderApp.payload.ResponsePaginationObject;

public interface RatingService {
	RatingDto createRating(long restaurantId, RatingDto toRate);

	RatingDto getRatingById(long restaurantId, long ratingId);

	ResponsePaginationObject<RatingDto> getRatingsByRestaurantId(long restaurantId, int pageNo, int pageSize,
			String sortBy, String sortDir);

	RatingDto updateRating(long restaurantId, long ratingId, RatingDto reqBody);

	void deleteRating(long restaurantId, long ratingId);
}
