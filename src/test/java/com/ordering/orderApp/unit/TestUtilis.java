package com.ordering.orderApp.unit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ordering.orderApp.payload.entities.CustomUserDetails;

public class TestUtilis {
	public static long createRestaurant(MockMvc mockMvc) throws Exception {
		String uniqueName = "test_name_" + UUID.randomUUID();
		MvcResult result = mockMvc
				.perform(post("/api/restaurants")
						.with(user(new CustomUserDetails("admin", "password", "admin@example.com",
								List.of(new SimpleGrantedAuthority("ROLE_ADMIN")), "Admin", "User")))
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\n" + "    \"name\":\"test name " + uniqueName + "\",\n"
								+ "    \"description\":\"test description\",\n" + "    \"address\":\"test address\",\n"
								+ "    \"imageUrl\":\"test img\"\n" + "}")
						.characterEncoding("utf-8"))
				.andExpect(status().isCreated()).andReturn();
		ObjectMapper objectMapper = new ObjectMapper();
		String responseContent = result.getResponse().getContentAsString();
		JsonNode responseJSON = objectMapper.readTree(responseContent);
		long restaurantId = responseJSON.at("/data/id").asLong();
		return restaurantId;
	}
}
