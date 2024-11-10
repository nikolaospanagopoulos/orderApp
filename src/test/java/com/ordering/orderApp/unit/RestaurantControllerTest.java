package com.ordering.orderApp.unit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.UUID;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RestaurantControllerTest {
	@Autowired
	private MockMvc mockMvc;
	long idOfRestaurant;

	@Test
	public void testGetAllRestaurants() throws Exception {
		this.mockMvc.perform(get("/api/restaurants/" + idOfRestaurant)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andDo(print());
	}

	@Test
	public void testGetRestaurant() throws Exception {
		this.mockMvc.perform(get("/api/restaurants/" + idOfRestaurant)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data.id").value(is((int) idOfRestaurant))).andDo(print());
	}

	@Test
	public void testUpdateRestaurant() throws Exception {
		String uniqueName = "test_name_" + UUID.randomUUID();
		MvcResult result = this.mockMvc
				.perform(put("/api/restaurants/" + idOfRestaurant).contentType(MediaType.APPLICATION_JSON)
						.content("{\n" + "    \"name\":\"test name " + uniqueName + "\",\n"
								+ "    \"description\":\"test description update\",\n"
								+ "    \"address\":\"test address\",\n" + "    \"imageUrl\":\"test img\"\n" + "}")
						.characterEncoding("utf-8"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.description").value(is("test description update"))).andReturn();

	}

	@Test
	public void testDeleteRestaurant() throws Exception {
		this.mockMvc.perform(delete("/api/restaurants/" + idOfRestaurant)).andExpect(status().isOk()).andDo(print());
	}

	@BeforeEach
	public void testCreateRestaurant() throws Exception {
		String uniqueName = "test_name_" + UUID.randomUUID();
		MvcResult result = this.mockMvc
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
		idOfRestaurant = restaurantId;
	}
}
