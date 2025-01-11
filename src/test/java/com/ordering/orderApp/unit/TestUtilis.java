package com.ordering.orderApp.unit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

	public static void createRoles(MockMvc mockMvc) throws Exception {
		mockMvc.perform(get("/api/auth/roles")).andExpect(status().isCreated()).andReturn();
	}

	public static void registerUser(MockMvc mockMvc) throws Exception {
		mockMvc.perform(post("/api/auth/register").contentType(MediaType.APPLICATION_JSON)
				.content("{\n" + "    \"firstName\": \"nikos\",\n" + "    \"lastName\": \"panago\",\n"
						+ "    \"password\": \"123456\",\n" + "     \"email\": \"panarasg254@gmail.com\",\n"
						+ "    \"username\": \"panaras251\"\n" + "}")
				.characterEncoding("utf-8")).andExpect(status().isCreated()).andReturn();
	}

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

	public static long createDish(MockMvc mockMvc, long idOfRestaurant) throws Exception {
		String uniqueName = "test_name_" + UUID.randomUUID();
		MvcResult mvcResult = mockMvc
				.perform(post("/api/restaurants/" + idOfRestaurant + "/dishes")
						.with(user(new CustomUserDetails("admin", "password", "admin@example.com",
								List.of(new SimpleGrantedAuthority("ROLE_ADMIN")), "Admin", "User")))
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\n" + "    \"name\":\"test dish name " + uniqueName + "\",\n"
								+ "    \"description\":\"test dish description\",\n" + "    \"price\":10.0,\n"
								+ "    \"createdDate\": \"2024-03-15\"\n" + "}")
						.characterEncoding("utf-8"))
				.andExpect(status().isCreated()).andReturn();
		ObjectMapper objectMapper = new ObjectMapper();
		String responseContent = mvcResult.getResponse().getContentAsString();
		JsonNode responseJson = objectMapper.readTree(responseContent);
		return responseJson.at("/data/id").asLong();
	}
}
