package com.ordering.orderApp.unit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtilis {
	public static long createRestaurant(MockMvc mockMvc) throws Exception {
		String uniqueName = "test_name_" + UUID.randomUUID();
		MvcResult result = mockMvc
				.perform(post("/api/restaurants").contentType(MediaType.APPLICATION_JSON)
						.content("{\n" + "    \"name\":\"test name " + uniqueName + "\",\n"
								+ "    \"description\":\"test description\",\n" + "    \"address\":\"test address\",\n"
								+ "    \"imageUrl\":\"test img\"\n" + "}")
						.characterEncoding("utf-8"))
				.andExpect(status().isCreated()).andReturn();
		ObjectMapper objectMapper = new ObjectMapper();
		String responseContent = result.getResponse().getContentAsString();
		JsonNode responseJSON = objectMapper.readTree(responseContent);
		long restaurantId = responseJSON.get("id").asLong();
		return restaurantId;
	}
}
