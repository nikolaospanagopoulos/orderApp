package com.ordering.orderApp.servicesImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ordering.orderApp.entities.Dish;
import com.ordering.orderApp.entities.Order;
import com.ordering.orderApp.entities.Restaurant;
import com.ordering.orderApp.entities.User;
import com.ordering.orderApp.exceptions.ResourceNotFoundException;
import com.ordering.orderApp.payload.DishDto;
import com.ordering.orderApp.payload.OrderDto;
import com.ordering.orderApp.payload.OrderItemDto;
import com.ordering.orderApp.payload.OrderResponseDto;
import com.ordering.orderApp.repositories.DishRepository;
import com.ordering.orderApp.repositories.OrderRepository;
import com.ordering.orderApp.repositories.RestaurantRepository;
import com.ordering.orderApp.repositories.UserRepository;
import com.ordering.orderApp.services.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	private UserRepository userRepository;
	private RestaurantRepository restaurantRepository;
	private DishRepository dishRepository;
	private OrderRepository orderRepository;
	private ModelMapper modelMapper;

	public OrderServiceImpl(UserRepository userRepository, RestaurantRepository restaurantRepository,
			DishRepository dishRepository, OrderRepository orderRepository, ModelMapper modelMapper) {
		super();
		this.userRepository = userRepository;
		this.restaurantRepository = restaurantRepository;
		this.dishRepository = dishRepository;
		this.orderRepository = orderRepository;
		this.modelMapper = modelMapper;
	}

	private DishDto mapToDish(Dish dish) {
		return modelMapper.map(dish, DishDto.class);
	}

	private OrderResponseDto OrderToOrderResponseDtoMap(Order order, User user) {

		OrderResponseDto orderResponseDto = new OrderResponseDto();

		orderResponseDto.setAddress(order.getAddress());
		orderResponseDto.setOrderTime(order.getOrderTime());
		orderResponseDto.setPostalCode(order.getPostalCode());
		orderResponseDto.setPrice(order.getTotalCost());
		List<OrderItemDto> orderItems = order.getOrderItems().stream().map(o -> {
			return new OrderItemDto(mapToDish(o.getDish()), o.getQuantity());
		}).collect(Collectors.toList());

		orderResponseDto.setOrderItems(orderItems);
		orderResponseDto.setUserFirstName(user.getUsername());
		orderResponseDto.setUserLastName(user.getLastName());
		orderResponseDto.setUsername(user.getUsername());
		return orderResponseDto;
	}

	@Override
	public OrderResponseDto createOrder(long restaurantId, OrderDto requestBody) {
		// TODO Auto-generated method stub
		System.out.println(requestBody);
		// maybe check cookie for user info??
		User found = userRepository.findByEmail(requestBody.getUserEmail())
				.orElseThrow(() -> new ResourceNotFoundException("User", "email", requestBody.getUserEmail()));
		System.out.println(found);
		Restaurant foundRestaurant = restaurantRepository.findById(requestBody.getRestaurantId()).orElseThrow(
				() -> new ResourceNotFoundException("Restaurant", "id", Long.toString(requestBody.getRestaurantId())));
		System.out.println(foundRestaurant.getName());

		Map<Dish, Integer> foundDishes = requestBody.getOrderDishesInfo().entrySet().stream().map((entry) -> {
			return Map.entry(
					dishRepository.findById(entry.getKey())
							.orElseThrow(() -> new ResourceNotFoundException("Dish", "id", entry.getKey().toString())),
					entry.getValue());
		}).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

		System.out.println(foundDishes);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String orderTime = LocalDateTime.now().format(formatter);
		String address = requestBody.getAddress();
		String postalCode = requestBody.getPostalCode();
		System.out.println(orderTime);

		Double totalCost = 0.0;

		for (var pair : foundDishes.entrySet()) {
			totalCost += (pair.getKey().getPrice() * pair.getValue());
		}
		System.out.println("total cost: " + totalCost);

		Order newOrder = new Order();
		newOrder.setAddress(address);
		newOrder.setOrderTime(orderTime);
		newOrder.setPostalCode(postalCode);
		newOrder.setTotalCost(totalCost);
		newOrder.setRestaurant(foundRestaurant);
		newOrder.setUser(found);
		newOrder.setStatus("RECEIVED");

		foundDishes.entrySet().stream().forEach(e -> {
			newOrder.addOrderItem(e.getKey(), e.getValue());
		});
		// TODO: continue adding properties to order entity
		orderRepository.save(newOrder);

		OrderResponseDto response = OrderToOrderResponseDtoMap(newOrder, found);

		return response;
	}

}
