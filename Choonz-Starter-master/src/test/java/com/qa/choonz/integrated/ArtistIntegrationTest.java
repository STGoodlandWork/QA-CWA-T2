package com.qa.choonz.integrated;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.rest.dto.ArtistDTO;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = { "classpath:task-schema.sql",
		"classpath:testdata.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class ArtistIntegrationTest {
	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper jsonifier;

	@Autowired
	private ModelMapper mapper;

	private ArtistDTO mapToDTO(Artist artist) {
		return this.mapper.map(artist, ArtistDTO.class);
	}

	ResultMatcher checkStatus;
	RequestBuilder request;

	private final String URI = "/artist/";

	private final Artist TEST_1 = new Artist("The Weeknd");
	private final Artist TEST_2 = new Artist(2l, "Daniel Caesar");
	private final Artist TEST_3 = new Artist(3l, "SZA");
	private final Artist TEST_4 = new Artist(4l, "Brent Faiyaz");
	private final Artist TEST_5 = new Artist(5l, "Mac Miller");

	@Test
	void createTest() throws Exception {
		ArtistDTO testDTO = mapToDTO(TEST_1);
		String testDTOAsJSON = jsonifier.writeValueAsString(testDTO);

		RequestBuilder request = post(URI + "create").contentType(MediaType.APPLICATION_JSON).content(testDTOAsJSON);
		ResultMatcher checkStatus = status().isCreated();

		ArtistDTO testSavedDTO = mapToDTO(TEST_1);
		testSavedDTO.setId(6l);
		String testSavedDTOAsJSON = jsonifier.writeValueAsString(testSavedDTO);

		ResultMatcher checkBody = content().json(testSavedDTOAsJSON);

		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);
	}

	@Test
	void readByIDTest() throws Exception {
		RequestBuilder request = get(URI + "read/" + TEST_2.getId());
		ResultMatcher checkStatus = status().isOk();
		ResultMatcher checkContent = content().contentType(MediaType.APPLICATION_JSON);
		ResultMatcher checkBody = jsonPath("name").value(TEST_2.getName());

		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkContent).andExpect(checkBody);
	}

	@Test
	void readAllTest() throws Exception {
		RequestBuilder request = get(URI + "read").contentType(MediaType.APPLICATION_JSON);
		ResultMatcher checkStatus = status().isOk();
		ResultMatcher checkBody = content().json("[" + "{\"id\":1,\"name\":\"The Weeknd\",\"albums\":[],\"tracks\":[]},"
				+ "{\"id\":2,\"name\":\"Daniel Caesar\",\"albums\":[],\"tracks\":[]},"
				+ "{\"id\":3,\"name\":\"SZA\",\"albums\":[],\"tracks\":[]},"
				+ "{\"id\":4,\"name\":\"Brent Faiyaz\",\"albums\":[],\"tracks\":[]},"
				+ "{\"id\":5,\"name\":\"Mac Miller\",\"albums\":[],\"tracks\":[]}" + "]");

		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);
	}

	@Test
	void updateTest() throws Exception {
		ArtistDTO testDTO = mapToDTO(TEST_3);
		String testDTOAsJSON = jsonifier.writeValueAsString(testDTO);

		RequestBuilder request = put(URI + "update/" + TEST_3.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(testDTOAsJSON);
		ResultMatcher checkStatus = status().isAccepted();

		ArtistDTO testSavedDTO = mapToDTO(TEST_3);

		ResultMatcher checkBody = jsonPath("name").value(testSavedDTO.getName());

		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);
	}

	@Test
	void deleteTest() throws Exception {
		RequestBuilder request = delete(URI + "delete/" + TEST_4.getId());
		ResultMatcher checkStatus = status().isNoContent();

		this.mvc.perform(request).andExpect(checkStatus);
	}

	@Test
	void searchTest() throws Exception {
		RequestBuilder request = get(URI + "search/" + TEST_5.getName()).contentType(MediaType.APPLICATION_JSON);
		ResultMatcher checkStatus = status().isOk();

		ResultMatcher checkBody = content().json("[{\"id\":5,\"name\":\"Mac Miller\",\"albums\":[],\"tracks\":[]}]");
		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);
	}

}
