package com.ordering.orderApp.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "restaurants", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
public class Restaurant {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	private String address;
	private String imageUrl;
	private double averageRating;
	private double totalRatingValue = 0.0;
	private int ratingCount = 0;

	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Dish> dishes;

	public void addRating(double ratingValue) {
		this.totalRatingValue += ratingValue;
		this.ratingCount++;
		updateAverageRating();
	}

	public void deleteRating(double ratingValue) {
		this.totalRatingValue -= ratingValue;
		this.ratingCount--;
		updateAverageRating();
	}

	public void updateRating(double oldRatingValue, double newRatingValue) {
		this.totalRatingValue += (newRatingValue - oldRatingValue);
		updateAverageRating();
	}

	private void updateAverageRating() {
		if (this.ratingCount > 0) {
			this.averageRating = Math.round((this.totalRatingValue / this.ratingCount) * 100) / 100.0;
		} else {
			this.averageRating = 0.0;
		}
	}

	public double getTotalRatingValue() {
		return totalRatingValue;
	}

	public void setTotalRatingValue(double totalRatingValue) {
		this.totalRatingValue = totalRatingValue;
	}

	public int getRatingCount() {
		return ratingCount;
	}

	public void setRatingCount(int ratingCount) {
		this.ratingCount = ratingCount;
	}

	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Rating> ratings;

	public Set<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(Set<Rating> ratings) {
		this.ratings = ratings;
	}

	public Restaurant(Long id, String name, String description, String address, String imageUrl, Set<Dish> dishes,
			Set<Rating> ratings, double averageRating) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.address = address;
		this.imageUrl = imageUrl;
		this.averageRating = 0.0;
		this.dishes = new HashSet<Dish>();
		this.ratings = new HashSet<Rating>();
	}

	public double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}

	public Restaurant() {

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

	public Set<Dish> getDishes() {
		return dishes;
	}

	public void setDishes(Set<Dish> dishes) {
		this.dishes = dishes;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
