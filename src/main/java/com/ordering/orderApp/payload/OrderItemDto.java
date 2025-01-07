package com.ordering.orderApp.payload;

public class OrderItemDto {
	private DishDto orderedDish;
	private int quantity;

	public OrderItemDto(DishDto orderedDish, int quantity) {
		super();
		this.orderedDish = orderedDish;
		this.quantity = quantity;
	}

	public OrderItemDto() {
		super();
	}

	public DishDto getOrderedDish() {
		return orderedDish;
	}

	public void setOrderedDish(DishDto orderedDish) {
		this.orderedDish = orderedDish;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
