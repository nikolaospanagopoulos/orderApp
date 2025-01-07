package com.ordering.orderApp.payload;

import java.util.HashMap;
import java.util.Map;

public class OrderDto {
	private String userEmail;
	private long restaurantId;
	private Map<Long, Integer> orderDishesInfo;
	private String status;
	private String postalCode;
	private String address;

	public OrderDto(String userEmail, long restaurantId, String status, String postalCode, String address) {
		super();
		this.userEmail = userEmail;
		this.restaurantId = restaurantId;
		this.orderDishesInfo = new HashMap<Long, Integer>();
		this.status = status;
		this.postalCode = postalCode;
		this.address = address;
	}

	public OrderDto() {
		super();
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public Map<Long, Integer> getOrderDishesInfo() {
		return orderDishesInfo;
	}

	public void setOrderDishesInfo(Map<Long, Integer> orderDishesInfo) {
		this.orderDishesInfo = orderDishesInfo;
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

	@Override
	public String toString() {
		return "OrderDto [userEmail=" + userEmail + ", restaurantId=" + restaurantId + ", orderDishesInfo="
				+ orderDishesInfo + ", status=" + status + ", postalCode=" + postalCode + ", address=" + address + "]";
	}

}
