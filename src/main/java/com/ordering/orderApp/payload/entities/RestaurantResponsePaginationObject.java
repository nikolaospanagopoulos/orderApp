package com.ordering.orderApp.payload.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ordering.orderApp.payload.ResponsePaginationObject;
import com.ordering.orderApp.payload.RestaurantDto;

public class RestaurantResponsePaginationObject extends ResponsePaginationObject<RestaurantDto> {
	@JsonProperty("restaurants")
	private List<RestaurantDto> data;

	public RestaurantResponsePaginationObject(List<RestaurantDto> data, int pageNumber, int pageSize,
			long totalElements, int totalPages, boolean last) {
		super(data, pageNumber, pageSize, totalElements, totalPages, last);
		this.data = data;
	}

}
