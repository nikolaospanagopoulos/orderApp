package com.ordering.orderApp.servicesImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ordering.orderApp.entities.Dish;
import com.ordering.orderApp.entities.Restaurant;
import com.ordering.orderApp.exceptions.ResourceAlreadyExistsException;
import com.ordering.orderApp.exceptions.ResourceNotFoundException;
import com.ordering.orderApp.payload.DishDto;
import com.ordering.orderApp.payload.DishResponsePaginationObject;
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
		DishDto res = modelMapper.map(saved, DishDto.class);
		return res;
	}

	private Dish getDishEntityById(long dishId) {
		return dishRepository.findById(dishId)
				.orElseThrow(() -> new ResourceNotFoundException("Dish", "id", Long.toString(dishId)));
	}

	@Override
	public DishDto getDishById(long restaurantId, long dishId) {
		Restaurant found = findRestaurantById(restaurantId);
		Dish foundDish = getDishEntityById(dishId);
		if (!foundDish.getRestaurant().getId().equals(found.getId())) {
			throw new ResourceNotFoundException("Dish", "id", Long.toString(dishId));
		}

		return modelMapper.map(foundDish, DishDto.class);
	}

	@Override
	public DishDto updateDish(long restaurantId, long dishId, DishDto reqBody) {
		Restaurant found = findRestaurantById(restaurantId);
		Dish foundDish = getDishEntityById(dishId);
		if (!foundDish.getRestaurant().getId().equals(found.getId())) {
			throw new ResourceNotFoundException("Dish", "id", Long.toString(dishId));
		}
		foundDish.setCreatedDate(reqBody.getCreatedDate());
		foundDish.setDescription(reqBody.getDescription());
		foundDish.setName(reqBody.getName());
		foundDish.setPrice(reqBody.getPrice());
		System.out.println(foundDish);
		Dish updated = dishRepository.save(foundDish);
		System.out.println(updated);
		return modelMapper.map(updated, DishDto.class);
	}

	@Override
	public DishResponsePaginationObject getDishesByRestaurantId(long restaurantId, int pageNo, int pageSize,
			String sortBy, String sortDir) {
	    findRestaurantById(restaurantId);
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortBy).descending()
				: Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<Dish> dishesPage = dishRepository.findByRestaurantId(restaurantId, pageable);
		List<DishDto> content = dishesPage.getContent().stream().map(d -> modelMapper.map(d, DishDto.class))
				.collect(Collectors.toList());
		DishResponsePaginationObject dishResponsePaginationObject = new DishResponsePaginationObject(content,
				dishesPage.getNumber(), dishesPage.getSize(), dishesPage.getTotalElements(), dishesPage.getTotalPages(),
				dishesPage.isLast());
		return dishResponsePaginationObject;
	}

	@Override
	public void deleteDishById(long restaurantId, long dishId) {
		Restaurant found = findRestaurantById(restaurantId);
		Dish foundDish = getDishEntityById(dishId);
		if (!foundDish.getRestaurant().getId().equals(found.getId())) {
			throw new ResourceNotFoundException("Dish", "id", Long.toString(dishId));
		}
		dishRepository.delete(foundDish);
	}

}
