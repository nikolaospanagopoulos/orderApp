package com.ordering.orderApp.servicesImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ordering.orderApp.entities.Restaurant;
import com.ordering.orderApp.entities.User;
import com.ordering.orderApp.exceptions.ResourceAlreadyExistsException;
import com.ordering.orderApp.exceptions.ResourceNotFoundException;
import com.ordering.orderApp.exceptions.UnauthorizedException;
import com.ordering.orderApp.payload.ResponsePaginationObject;
import com.ordering.orderApp.payload.RestaurantDto;
import com.ordering.orderApp.payload.entities.CustomUserDetails;
import com.ordering.orderApp.payload.entities.RestaurantResponsePaginationObject;
import com.ordering.orderApp.repositories.RestaurantRepository;
import com.ordering.orderApp.repositories.UserRepository;
import com.ordering.orderApp.services.RestaurantService;

@Service
public class RestaurantServiceImpl implements RestaurantService {
	ModelMapper modelMapper;
	RestaurantRepository restaurantRepository;
	UserRepository userRepository;

	public RestaurantServiceImpl(ModelMapper modelMapper, RestaurantRepository restaurantRepository,
			UserRepository userRepository) {
		super();
		this.modelMapper = modelMapper;
		this.restaurantRepository = restaurantRepository;
		this.userRepository = userRepository;
	}

	private Restaurant mapToRestaurantEntity(RestaurantDto dto) {
		Restaurant restaurant = modelMapper.map(dto, Restaurant.class);

		// Initialize owners manually if needed
		if (dto.getOwners() != null) {
			Set<User> owners = dto.getOwners().stream().map(ownerDto -> {
				User user = userRepository.findByEmail(ownerDto.getEmail())
						.orElseThrow(() -> new ResourceNotFoundException("User", "email", ownerDto.getEmail()));
				return user;
			}).collect(Collectors.toSet());
			restaurant.setOwners(owners);
		} else {
			restaurant.setOwners(new HashSet<User>());
		}

		return restaurant;
	}

	private RestaurantDto mapToRestaurantDto(Restaurant restaurant) {
		return modelMapper.map(restaurant, RestaurantDto.class);
	}

	@Override
	public RestaurantDto createRestaurant(RestaurantDto toCreate) {
		System.out.println(toCreate);
		// TODO: check user username and emails exist, and role is OWNER
		if (restaurantRepository.existsByName(toCreate.getName())) {
			throw new ResourceAlreadyExistsException("Restaurant", "name", toCreate.getName());
		}

		// TODO: check whole implementation!
		Restaurant toSave = mapToRestaurantEntity(toCreate);
		if (toCreate.getOwners() != null) {
			Set<User> owners = toCreate.getOwners().stream().map(o -> {
				return userRepository.findByEmail(o.getEmail())
						.orElseThrow(() -> new ResourceNotFoundException("User", "email", o.getEmail()));
			}).collect(Collectors.toSet());

			System.out.println(owners);

			toSave.addOwner(owners);
		}

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
	public ResponsePaginationObject<RestaurantDto> getRestaurants(int pageNo, int pageSize, String sortBy,
			String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortBy).descending()
				: Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<Restaurant> restaurants = restaurantRepository.findAll(pageable);
		List<Restaurant> listOfRestaurants = restaurants.getContent();
		List<RestaurantDto> content = listOfRestaurants.stream().map(r -> mapToRestaurantDto(r))
				.collect(Collectors.toList());
		return new RestaurantResponsePaginationObject

		(content, restaurants.getNumber(), restaurants.getSize(), restaurants.getTotalElements(),
				restaurants.getTotalPages(), restaurants.isLast());
	}

	@Override
	public RestaurantDto updateRestaurant(long restaurantId, RestaurantDto updated) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		boolean userIsAdmin = userDetails.getAuthorities().stream()
				.anyMatch(a -> a.getAuthority().equalsIgnoreCase("ROLE_ADMIN"));
		String loggedInUsername = userDetails.getUsername();
		Restaurant found = getRestaurantEntityById(restaurantId);
		boolean userIsOwner = found.getOwners().stream().anyMatch(o -> {
			return o.getUsername().equals(loggedInUsername) && o.getEmail().equals(userDetails.getEmail());
		});

		if (!userIsOwner && !userIsAdmin) {
			throw new UnauthorizedException("Unauthorized");
		}
		found.setAddress(updated.getAddress());
		found.setAverageRating(updated.getAverageRating());
		found.setDescription(updated.getDescription());
		found.setImageUrl(updated.getImageUrl());
		found.setName(updated.getName());
		if (updated.getOwners() != null) {
			Set<User> owners = updated.getOwners().stream().map(o -> {
				return userRepository.findByEmail(o.getEmail())
						.orElseThrow(() -> new ResourceNotFoundException("User", "email", o.getEmail()));
			}).collect(Collectors.toSet());
			found.addOwner(owners);
		}
		Restaurant savedRes = restaurantRepository.save(found);
		return mapToRestaurantDto(savedRes);
	}

	@Override
	public void deleteRestaurantById(long restautantId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		boolean userIsAdmin = userDetails.getAuthorities().stream()
				.anyMatch(a -> a.getAuthority().equalsIgnoreCase("ROLE_ADMIN"));
		String loggedInUsername = userDetails.getUsername();
		Restaurant found = getRestaurantEntityById(restautantId);
		boolean userIsOwner = found.getOwners().stream().anyMatch(o -> {
			return o.getUsername().equals(loggedInUsername) && o.getEmail().equals(userDetails.getEmail());
		});
		if (!userIsOwner && !userIsAdmin) {
			throw new UnauthorizedException("Unauthorized");
		}
		restaurantRepository.delete(found);
	}

	@Override
	public RestaurantDto getRestaurantById(long restaurantId) {
		Restaurant found = getRestaurantEntityById(restaurantId);
		return modelMapper.map(found, RestaurantDto.class);
	}

}
