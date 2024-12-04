package com.ordering.orderApp.unit;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RatingControllerTest {
	@Autowired
	private MockMvc mockMvc;
	long idOfRestaurant;
	long idOfRating;

	@BeforeEach
	public void createRestaurantPrep() throws Exception {
		idOfRestaurant = TestUtilis.createRestaurant(this.mockMvc);
		createRating();
	}

	@Test
	public void testGetAllRatingsByRestaurantId() throws Exception {
		createRating();
		createRating();
		MvcResult result = this.mockMvc.perform(get("/api/restaurants/" + idOfRestaurant + "/ratings"))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data.ratings").isArray()).andReturn();
		String content = result.getResponse().getContentAsString();
		JsonNode jsonNode = new ObjectMapper().readTree(content);
		List<Long> ids = new ArrayList<>();
		for (JsonNode ratingNode : jsonNode.at("/data/ratings")) {
			ids.add(ratingNode.get("id").asLong());
		}
		// Check that the list of IDs is sorted in descending order
		for (int i = 0; i < ids.size() - 1; i++) {
			assertTrue(ids.get(i) > ids.get(i + 1), "Ratings are not sorted by ID in descending order");
		}
	}

	@Test
	public void testUpdateRating() throws Exception {
		this.mockMvc
				.perform(put("/api/restaurants/" + idOfRestaurant + "/ratings/" + idOfRating)
						.with(user("admin").roles("ADMIN")).contentType(MediaType.APPLICATION_JSON)
						.content("{\n" + "    \"ratingValue\":\"3.5\",\n" + "    \"review\":\"test review\"\n" + "}"))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data.ratingValue").value(is(3.5)))
				.andExpect(jsonPath("$.data.review").value(is("test review"))).andReturn();

	}

	@Test
	public void testDeleteRating() throws Exception {
		this.mockMvc
				.perform(delete("/api/restaurants/" + idOfRestaurant + "/ratings/" + idOfRating)
						.with(user("admin").roles("ADMIN")))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
	}

	@Test
	public void testGetRatingById() throws Exception {
		this.mockMvc.perform(get("/api/restaurants/" + idOfRestaurant + "/ratings/" + idOfRating))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data.id").value(is((int) idOfRating))).andReturn();
	}

	private void createRating() throws Exception {
		MvcResult mvcResult = this.mockMvc
				.perform(
						post("/api/restaurants/" + idOfRestaurant + "/ratings").with(user("admin").roles("ADMIN"))
								.contentType(MediaType.APPLICATION_JSON).content("{\n" + "    \"ratingValue\":\"5\",\n"
										+ "    \"review\":\"test review\"\n" + "}")
								.characterEncoding("utf-8"))
				.andExpect(status().isCreated()).andReturn();

		String responseContent = mvcResult.getResponse().getContentAsString();

		JsonNode jsonNode = new ObjectMapper().readTree(responseContent);
		idOfRating = jsonNode.path("data").path("id").asLong();

	}
}
