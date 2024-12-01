package com.ordering.orderApp.servicesImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ordering.orderApp.entities.Rating;
import com.ordering.orderApp.entities.Restaurant;
import com.ordering.orderApp.exceptions.ResourceNotFoundException;
import com.ordering.orderApp.payload.RatingDto;
import com.ordering.orderApp.payload.ResponsePaginationObject;
import com.ordering.orderApp.payload.entities.RatingResponsePaginationObject;
import com.ordering.orderApp.repositories.RatingRepository;
import com.ordering.orderApp.repositories.RestaurantRepository;
import com.ordering.orderApp.services.RatingService;

import jakarta.transaction.Transactional;

@Service
public class RatingServiceImpl implements RatingService {

	RestaurantRepository restaurantRepository;
	RatingRepository ratingRepository;
	ModelMapper modelMapper;

	public RatingServiceImpl(RestaurantRepository restaurantRepository, RatingRepository ratingRepository,
			ModelMapper modelMapper) {
		super();
		this.restaurantRepository = restaurantRepository;
		this.ratingRepository = ratingRepository;
		this.modelMapper = modelMapper;
	}

	private Restaurant findRestaurantById(long id) {
		return restaurantRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Restaurant", "id", Long.toString(id)));
	}

	@Override
	public RatingDto updateRating(long restaurantId, long ratingId, RatingDto reqBody) {
		Restaurant foundRestaurant = findRestaurantById(restaurantId);
		Rating foundRating = getRatingEntityById(ratingId);
		if (!foundRating.getRestaurant().getId().equals(foundRestaurant.getId())) {
			throw new ResourceNotFoundException("Rating", "id", Long.toString(ratingId));
		}
		double oldRatingValue = foundRating.getRatingValue();
		foundRating.setRatingValue(reqBody.getRatingValue());
		foundRating.setReview(reqBody.getReview());
		Rating updated = ratingRepository.save(foundRating);

		foundRestaurant.updateRating(oldRatingValue, reqBody.getRatingValue());
		restaurantRepository.save(foundRestaurant);
		return modelMapper.map(updated, RatingDto.class);
	}

	@Override
	@Transactional
	public RatingDto createRating(long restaurantId, RatingDto toRate) {
		Restaurant found = findRestaurantById(restaurantId);
		Rating toSave = modelMapper.map(toRate, Rating.class);
		toSave.setRestaurant(found);

		Rating saved = ratingRepository.save(toSave);

		found.getRatings().add(toSave);
		found.addRating(toRate.getRatingValue());
		// TODO: later make each rating unique for each user

		restaurantRepository.save(found);

		return modelMapper.map(saved, RatingDto.class);
	}

	private Rating getRatingEntityById(long ratingId) {
		return ratingRepository.findById(ratingId)
				.orElseThrow(() -> new ResourceNotFoundException("Rating", "id", Long.toString(ratingId)));
	}

	public ResponsePaginationObject<RatingDto> getRatingsByRestaurantId(long restaurantId, int pageNo, int pageSize,
			String sortBy, String sortDir) {

		findRestaurantById(restaurantId);
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortBy).descending()
				: Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<Rating> ratingsPage = ratingRepository.findByRestaurantId(restaurantId, pageable);
		List<RatingDto> content = ratingsPage.getContent().stream().map(r -> modelMapper.map(r, RatingDto.class))
				.collect(Collectors.toList());
		RatingResponsePaginationObject responsePaginationObject = new RatingResponsePaginationObject(content,
				ratingsPage.getNumber(), ratingsPage.getSize(), ratingsPage.getTotalElements(),
				ratingsPage.getTotalPages(), ratingsPage.isLast());

		return responsePaginationObject;
	}

	@Override
	public RatingDto getRatingById(long restaurantId, long ratingId) {
		Restaurant foundRestaurant = findRestaurantById(restaurantId);
		Rating foundRating = getRatingEntityById(ratingId);
		if (!foundRating.getRestaurant().getId().equals(foundRestaurant.getId())) {
			throw new ResourceNotFoundException("Rating", "id", Long.toString(ratingId));
		}
		return modelMapper.map(foundRating, RatingDto.class);
	}

	@Override
	@Transactional
	public void deleteRating(long restaurantId, long ratingId) {
		Restaurant found = findRestaurantById(restaurantId);
		Rating foundRating = getRatingEntityById(ratingId);
		if (!foundRating.getRestaurant().getId().equals(found.getId())) {
			throw new ResourceNotFoundException("Rating", "id", Long.toString(ratingId));
		}
		found.deleteRating(foundRating.getRatingValue());
		restaurantRepository.save(found);
		ratingRepository.delete(foundRating);

	}

}
