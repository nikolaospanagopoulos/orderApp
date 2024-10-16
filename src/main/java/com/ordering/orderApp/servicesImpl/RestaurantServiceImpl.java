package com.ordering.orderApp.servicesImpl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ordering.orderApp.entities.Restaurant;
import com.ordering.orderApp.exceptions.ResourceAlreadyExistsException;
import com.ordering.orderApp.payload.RestaurantDto;
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

}
