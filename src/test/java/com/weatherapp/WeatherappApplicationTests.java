package com.weatherapp;

import com.weatherapp.util.UrlMappings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class WeatherappApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testWeatherInfoForCorrectCity() throws Exception {
		 MvcResult result =  this.mockMvc
				.perform(get(UrlMappings.APP_ROOT+ UrlMappings.CITY)
				.param("name","Ludwigsburg"))
				 .andDo(
				 		document("weather",requestParameters(
				 				parameterWithName("name").description("The city name whose weather information is requested")
						)))
				 .andDo(print())
				.andExpect(status().isOk())
				.andReturn();

		 //Weather information should not be null because of right information
		 Assertions.assertNotNull(result.getResponse());
	}

	@Test
	void testWeatherInfoForInvalidCityName() throws Exception {

		//City Name should be alphabets checking with invalid city name
		this.mockMvc
				.perform(get(UrlMappings.APP_ROOT+ UrlMappings.CITY)
						.param("name","1234567"))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(content().string(containsString("Invalid City Name")))
				.andReturn();
	}

	@Test
	void testWeatherInfoForWrongCityName() throws Exception {

		//Expect 404 . Testing with a city that does not exist
		this.mockMvc
				.perform(get(UrlMappings.APP_ROOT+ UrlMappings.CITY)
						.param("name","DummyCity"))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(content().string(containsString("city not found")))
				.andReturn();
	}
}
