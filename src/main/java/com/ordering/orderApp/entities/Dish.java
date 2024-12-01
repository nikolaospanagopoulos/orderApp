package com.ordering.orderApp.entities;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "dishes", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
public class Dish {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String description;
	private double price;
	private LocalDate createdDate;

	@Override
	public String toString() {
		return "Dish [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", createdDate=" + createdDate + ", restaurant=" + restaurant + "]";
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "restaurant_id", nullable = false)
	private Restaurant restaurant;

	public Dish(long id, String name, String description, double price, LocalDate createdDate, Restaurant restaurant) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.createdDate = createdDate;
		this.restaurant = restaurant;
	}

	public Dish() {
		super();

	}

	public Restaurant getRestaurant() {
		return restaurant;
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

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, restaurant != null ? restaurant.getId() : null);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Dish other = (Dish) obj;
		return Objects.equals(name, other.name) && Objects.equals(restaurant != null ? restaurant.getId() : null,
				other.restaurant != null ? other.restaurant.getId() : null);
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

}
