package com.ordering.orderApp.payload;

import java.util.List;

public class DishResponsePaginationObject {
	private List<DishDto> dishes;
	private int pageNumber;
	private int pageSize;
	private long totalElements;
	private int totalPages;
	private boolean last;

	public DishResponsePaginationObject(List<DishDto> dishes, int pageNumber, int pageSize, long totalElements,
			int totalPages, boolean last) {
		super();
		this.dishes = dishes;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalElements = totalElements;
		this.totalPages = totalPages;
		this.last = last;
	}

	public List<DishDto> getDishes() {
		return dishes;
	}

	public void setDishes(List<DishDto> dishes) {
		this.dishes = dishes;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public boolean isLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

}
