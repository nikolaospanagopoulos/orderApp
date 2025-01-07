package com.ordering.orderApp.payload;

import java.util.List;

public class OrderResponseDto {
	private List<OrderItemDto> orderItems;
	private double price;
	private String status;
	private String postalCode;
	private String address;
	private String orderTime;
	private String username;
	private String userFirstName;
	private String userLastName;

	public OrderResponseDto(List<OrderItemDto> orderItems, double price, String status, String postalCode,
			String address, String orderTime, String username, String userFirstName, String userLastName) {
		super();
		this.orderItems = orderItems;
		this.price = price;
		this.status = status;
		this.postalCode = postalCode;
		this.address = address;
		this.orderTime = orderTime;
		this.username = username;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
	}

	public OrderResponseDto() {
		super();
	}

	public List<OrderItemDto> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemDto> orderItems) {
		this.orderItems = orderItems;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

}
