package com.ordering.orderApp.unit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DishControllerTest {
	@Autowired
	private MockMvc mockMvc;
	long idOfRestaurant;
	long idOfDish;

	@Test
	public void testGetDishById() throws Exception {
		this.mockMvc.perform(get("/api/restaurants/" + idOfRestaurant + "/dishes/" + idOfDish))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data.id").value(is((int) idOfDish)))
				.andExpect(jsonPath("$.data.restaurant.id").value(is((int) idOfRestaurant))).andDo(print());
	}

	@Test
	public void testDeleteDish() throws Exception {
		this.mockMvc.perform(delete("/api/restaurants/" + idOfRestaurant + "/dishes/" + idOfDish))
				.andExpect(status().isOk()).andDo(print());
	}

	@Test
	public void testGetAllDishesByRestaurantId() throws Exception {
		testCreateDish();
		testCreateDish();
		MvcResult result = this.mockMvc.perform(get("/api/restaurants/" + idOfRestaurant + "/dishes"))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data.dishes").isArray()).andReturn();
		String content = result.getResponse().getContentAsString();
		JsonNode jsonNode = new ObjectMapper().readTree(content);
		List<Long> ids = new ArrayList<>();
		for (JsonNode dishNode : jsonNode.at("/data/dishes")) {
			ids.add(dishNode.get("id").asLong());
		}

		// Check that the list of IDs is sorted in descending order
		for (int i = 0; i < ids.size() - 1; i++) {
			assertTrue(ids.get(i) > ids.get(i + 1), "Dishes are not sorted by ID in descending order");
		}
	}

	@BeforeEach
	public void testCreateRestaurantAndDish() throws Exception {
		createRestaurant();
		testCreateDish();
	}

	private void createRestaurant() throws Exception {
		idOfRestaurant = TestUtilis.createRestaurant(this.mockMvc);

	}

	@Test
	public void testUpdateDish() throws Exception {
		String uniqueName = "test_name_" + UUID.randomUUID();
		this.mockMvc
				.perform(put("/api/restaurants/" + idOfRestaurant + "/dishes/" + idOfDish)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\n" + "    \"name\":\"test dish name " + uniqueName + "updated\",\n"
								+ "    \"description\":\"test dish description updated\",\n" + "    \"price\":10.0,\n"
								+ "    \"createdDate\": \"2024-03-15\"\n" + "}"))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))

				.andExpect(jsonPath("$.data.description").value(is("test dish description updated")))
				.andExpect(jsonPath("$.data.name").value((containsString("updated")))).andReturn();
		;
	}

	private void testCreateDish() throws Exception {
		String uniqueName = "test_name_" + UUID.randomUUID();
		MvcResult mvcResult = this.mockMvc
				.perform(post("/api/restaurants/" + idOfRestaurant + "/dishes").contentType(MediaType.APPLICATION_JSON)
						.content("{\n" + "    \"name\":\"test dish name " + uniqueName + "\",\n"
								+ "    \"description\":\"test dish description\",\n" + "    \"price\":10.0,\n"
								+ "    \"createdDate\": \"2024-03-15\"\n" + "}")
						.characterEncoding("utf-8"))
				.andExpect(status().isCreated()).andReturn();
		ObjectMapper objectMapper = new ObjectMapper();
		String responseContent = mvcResult.getResponse().getContentAsString();
		JsonNode responseJson = objectMapper.readTree(responseContent);
		idOfDish = responseJson.get("id").asLong();
	}
}
