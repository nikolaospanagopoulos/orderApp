package com.ordering.orderApp.payload;

public class RestaurantDto {
	private Long id;
	private String name;
	private String description;
	private String address;
	private String imageUrl;
	private double averageRating;

	public RestaurantDto(Long id, String name, String description, String address, String imageUrl,
			double averageRating) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.address = address;
		this.imageUrl = imageUrl;
		this.averageRating = averageRating;
	}

	public double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}

	public RestaurantDto() {

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
