package com.ordering.orderApp.payload;

import java.time.LocalDate;

public class DishDto {
	private Long id;
	private String name;
	private String description;
	private double price;
	private LocalDate createdDate;
	private RestaurantDto restaurant;

	public DishDto(Long id, String name, String description, double price, LocalDate createdAt,
			RestaurantDto restaurant) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.createdDate = createdAt;
		this.restaurant = restaurant;

	}

	@Override
	public String toString() {
		return "DishDto [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", createdDate=" + createdDate + ", restaurant=" + restaurant + "]";
	}

	public RestaurantDto getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(RestaurantDto restaurant) {
		this.restaurant = restaurant;
	}

	public DishDto() {
		super();

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

}
