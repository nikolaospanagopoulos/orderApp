package com.ordering.orderApp.payload.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.ordering.orderApp.payload.RatingDto;
import com.ordering.orderApp.payload.ResponsePaginationObject;

public class RatingResponsePaginationObject extends ResponsePaginationObject<RatingDto> {
	@JsonProperty("ratings")
	private List<RatingDto> data;

	public RatingResponsePaginationObject(List<RatingDto> data, int pageNumber, int pageSize, long totalElements,
			int totalPages, boolean last) {
		super(data, pageNumber, pageSize, totalElements, totalPages, last);
		this.data = data;
	}

}
