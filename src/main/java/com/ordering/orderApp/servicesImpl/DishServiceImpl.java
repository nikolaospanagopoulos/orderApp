package com.ordering.orderApp.servicesImpl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ordering.orderApp.entities.Dish;
import com.ordering.orderApp.entities.Restaurant;
import com.ordering.orderApp.exceptions.ResourceAlreadyExistsException;
import com.ordering.orderApp.exceptions.ResourceNotFoundException;
import com.ordering.orderApp.payload.DishDto;
import com.ordering.orderApp.repositories.DishRepository;
import com.ordering.orderApp.repositories.RestaurantRepository;
import com.ordering.orderApp.services.DishService;

@Service
public class DishServiceImpl implements DishService {

	RestaurantRepository restaurantRepository;
	DishRepository dishRepository;
	ModelMapper modelMapper;

	public DishServiceImpl(RestaurantRepository restaurantRepository, DishRepository dishRepository,
			ModelMapper modelMapper) {
		super();
		this.restaurantRepository = restaurantRepository;
		this.dishRepository = dishRepository;
		this.modelMapper = modelMapper;
	}

	private Restaurant findRestaurantById(long id) {
		return restaurantRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Restaurant", "id", Long.toString(id)));
	}

	@Override
	public DishDto createDish(long restaurantId, DishDto toCreate) {
		Restaurant foundRestaurant = findRestaurantById(restaurantId);
		if (dishRepository.existsByName(toCreate.getName())) {
			throw new ResourceAlreadyExistsException("Dish", "name", toCreate.getName());
		}
		Dish newDish = modelMapper.map(toCreate, Dish.class);
		newDish.setRestaurant(foundRestaurant);
		Dish saved = dishRepository.save(newDish);
		return modelMapper.map(saved, DishDto.class);
	}

}
