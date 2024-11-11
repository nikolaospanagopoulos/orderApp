package com.ordering.orderApp.unit;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
	public void testGetRatingById() throws Exception {
		this.mockMvc.perform(get("/api/restaurants/" + idOfRestaurant + "/ratings/" + idOfRating))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data.id").value(is((int) idOfRating))).andReturn();
	}

	private void createRating() throws Exception {
		MvcResult mvcResult = this.mockMvc
				.perform(post("/api/restaurants/" + idOfRestaurant + "/ratings").contentType(MediaType.APPLICATION_JSON)
						.content("{\n" + "    \"ratingValue\":\"5\",\n" + "    \"review\":\"test review\"\n" + "}")
						.characterEncoding("utf-8"))
				.andExpect(status().isCreated()).andReturn();

		String responseContent = mvcResult.getResponse().getContentAsString();

		JsonNode jsonNode = new ObjectMapper().readTree(responseContent);
		idOfRating = jsonNode.path("data").path("id").asLong();
		
	}
}
