package com.ordering.orderApp.payload;

public class RatingDto {
	private long id;
	private double ratingValue;
	private String review;

	public RatingDto(long id, double ratingValue, String review) {
		super();
		this.id = id;
		this.ratingValue = ratingValue;
		this.review = review;
	}

	public RatingDto() {
		super();

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getRatingValue() {
		return ratingValue;
	}

	public void setRatingValue(double ratingValue) {
		this.ratingValue = ratingValue;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

}
