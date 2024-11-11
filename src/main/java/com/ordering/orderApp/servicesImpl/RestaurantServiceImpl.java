package com.ordering.orderApp.servicesImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ordering.orderApp.entities.Restaurant;
import com.ordering.orderApp.exceptions.ResourceAlreadyExistsException;
import com.ordering.orderApp.exceptions.ResourceNotFoundException;
import com.ordering.orderApp.payload.RestaurantDto;
import com.ordering.orderApp.payload.RestaurantResponsePaginationObject;
import com.ordering.orderApp.repositories.RestaurantRepository;
import com.ordering.orderApp.services.RestaurantService;

@Service
public class RestaurantServiceImpl implements RestaurantService {
	ModelMapper modelMapper;
	RestaurantRepository restaurantRepository;

	public RestaurantServiceImpl(ModelMapper modelMapper, RestaurantRepository restaurantRepository) {
		super();
		this.modelMapper = modelMapper;
		this.restaurantRepository = restaurantRepository;
	}

	private Restaurant mapToRestaurantEntity(RestaurantDto dto) {
		return modelMapper.map(dto, Restaurant.class);
	}

	private RestaurantDto mapToRestaurantDto(Restaurant restaurant) {
		return modelMapper.map(restaurant, RestaurantDto.class);
	}

	@Override
	public RestaurantDto createRestaurant(RestaurantDto toCreate) {
		if (restaurantRepository.existsByName(toCreate.getName())) {
			throw new ResourceAlreadyExistsException("Restaurant", "name", toCreate.getName());
		}
		Restaurant toSave = mapToRestaurantEntity(toCreate);
		Restaurant saved = restaurantRepository.save(toSave);
		return mapToRestaurantDto(saved);
	}

	private Restaurant getRestaurantEntityById(long id) {
		return restaurantRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Restaurant", "id", Long.toString(id)));
	}

	public RestaurantDto getRestaurantDtoByID(long id) {
		Restaurant found = getRestaurantEntityById(id);
		return modelMapper.map(found, RestaurantDto.class);
	}

	@Override
	public RestaurantResponsePaginationObject getRestaurants(int pageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortBy).descending()
				: Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<Restaurant> restaurants = restaurantRepository.findAll(pageable);
		List<Restaurant> listOfRestaurants = restaurants.getContent();
		List<RestaurantDto> content = listOfRestaurants.stream().map(r -> mapToRestaurantDto(r))
				.collect(Collectors.toList());
		return new RestaurantResponsePaginationObject(content, restaurants.getNumber(), restaurants.getSize(),
				restaurants.getTotalElements(), restaurants.getTotalPages(), restaurants.isLast());
	}

	@Override
	public RestaurantDto updateRestaurant(long restaurantId, RestaurantDto updated) {
		Restaurant found = getRestaurantEntityById(restaurantId);
		found.setAddress(updated.getAddress());
		found.setAverageRating(updated.getAverageRating());
		found.setDescription(updated.getDescription());
		found.setImageUrl(updated.getImageUrl());
		found.setName(updated.getName());
		Restaurant savedRes = restaurantRepository.save(found);
		return mapToRestaurantDto(savedRes);
	}

	@Override
	public RestaurantDto getRestaurantById(long restaurantId) {
		Restaurant found = getRestaurantEntityById(restaurantId);
		return modelMapper.map(found, RestaurantDto.class);
	} 

	@Override
	public void deleteRestaurantById(long restautantId) {
		Restaurant found = getRestaurantEntityById(restautantId);
		restaurantRepository.delete(found);
	}

}
