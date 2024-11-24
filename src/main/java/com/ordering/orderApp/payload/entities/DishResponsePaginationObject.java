package com.ordering.orderApp.payload.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ordering.orderApp.payload.DishDto;
import com.ordering.orderApp.payload.ResponsePaginationObject;

public class DishResponsePaginationObject extends ResponsePaginationObject<DishDto> {
	@JsonProperty("dishes")
	private List<DishDto> data;

	public DishResponsePaginationObject(List<DishDto> data, int pageNumber, int pageSize, long totalElements,
			int totalPages, boolean last) {
		super(data, pageNumber, pageSize, totalElements, totalPages, last);
		this.data = data;
	}

}
