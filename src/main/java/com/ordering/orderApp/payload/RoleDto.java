package com.ordering.orderApp.payload;

import java.util.Set;

public class RoleDto {
	private Set<String> roles;

	public RoleDto(Set<String> roles) {
		super();
		this.roles = roles;
	}

	public RoleDto() {
		super();

	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

}
