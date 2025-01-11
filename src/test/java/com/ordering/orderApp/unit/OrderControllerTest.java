package com.ordering.orderApp.unit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import static org.hamcrest.Matchers.startsWith;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.ordering.orderApp.payload.entities.CustomUserDetails;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OrderControllerTest {
	@Autowired
	private MockMvc mockMvc;
	private long idOfDish;
	private long idOfRestaurant;

	@BeforeEach
	private void testCreateRestaurantAndDish() throws Exception {

		idOfRestaurant = TestUtilis.createRestaurant(mockMvc);
		idOfDish = TestUtilis.createDish(mockMvc, idOfRestaurant);
		TestUtilis.createRoles(mockMvc);
		TestUtilis.registerUser(mockMvc);
	}

	@Test
	public void testCreateOrder() throws Exception {

		MvcResult mvcResult = mockMvc
				.perform(post("/api/restaurants/" + idOfRestaurant + "/orders")
						.with(user(new CustomUserDetails("panaras251", "123456", "panarasg254@gmail.com",
								List.of(new SimpleGrantedAuthority("ROLE_USER")), "nikos", "panago")))
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\n" + "    \"userEmail\":\"panarasg254@gmail.com\",\n" + "    \"restaurantId\":"
								+ idOfRestaurant + ",\n" + "    \"orderDishesInfo\":{\n" + "        \"" + idOfDish
								+ "\":3\n" + "    },\n" + "    \"status\":\"SENT\",\n"
								+ "    \"postalCode\":\"26225\",\n" + "    \"address\":\"test address\"\n" + "}")
						.characterEncoding("utf-8"))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.data.orderItems[0].quantity").value(3))
				.andExpect(jsonPath("$.data.orderItems[0].orderedDish.name")
						.value(startsWith("test dish name test_name_")))
				.andExpect(jsonPath("$.data.orderItems[0].orderedDish.price").value(10.0))
				.andExpect(jsonPath("$.data.orderItems[0].orderedDish.restaurant.name")
						.value(startsWith("test name test_name_")))
				.andReturn();

	}

}
