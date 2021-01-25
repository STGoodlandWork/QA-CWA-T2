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
import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.rest.dto.GenreDTO;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = { "classpath:task-schema.sql",
		"classpath:testdata.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class GenreIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper jsonifier;

	@Autowired
	private ModelMapper mapper;

	private GenreDTO mapToDTO(Genre genre) {
		return this.mapper.map(genre, GenreDTO.class);
	}

	ResultMatcher checkStatus;
	RequestBuilder request;

	private final String URI = "/genre/";

	private final Genre TEST_1 = new Genre("Rap", "Rhymthm and Poetry music", null);
	private final Genre TEST_2 = new Genre(2l, "Pop", "Music for the Radio", null);
	private final Genre TEST_3 = new Genre(3l, "Country", "Lil Nas X created this", null);
	private final Genre TEST_4 = new Genre(4l, "House", "Good Vibes Music", null);
	private final Genre TEST_5 = new Genre(5l, "R&B", "Heartbroken people love this", null);

	@Test
	void createTest() throws Exception {
		GenreDTO testDTO = mapToDTO(TEST_1);
		String testDTOAsJSON = jsonifier.writeValueAsString(testDTO);

		RequestBuilder request = post(URI + "create").contentType(MediaType.APPLICATION_JSON).content(testDTOAsJSON);
		ResultMatcher checkStatus = status().isCreated();

		GenreDTO testSavedDTO = mapToDTO(TEST_1);
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

		ResultMatcher checkBody = content().json("["
				+ "{\"id\":1,\"name\":\"Rap\",\"description\":\"Rhymthm and Poetry music\",\"albums\":[]},"
				+ "{\"id\":2,\"name\":\"Pop\",\"description\":\"Music for the Radio\",\"albums\":[]},"
				+ "{\"id\":3,\"name\":\"Country\",\"description\":\"Lil Nas X created this\",\"albums\":[]},"
				+ "{\"id\":4,\"name\":\"House\",\"description\":\"Good Vibes Music\",\"albums\":[]},"
				+ "{\"id\":5,\"name\":\"R&B\",\"description\":\"Heartbroken people love this\",\"albums\":[]}" + "]");
		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);
	}

	@Test
	void updateTest() throws Exception {
		GenreDTO testDTO = mapToDTO(new Genre("Bops"));
		String testDTOAsJSON = jsonifier.writeValueAsString(testDTO);

		RequestBuilder request = put(URI + "update/" + TEST_3.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(testDTOAsJSON);
		ResultMatcher checkStatus = status().isAccepted();

		GenreDTO testSavedDTO = mapToDTO(new Genre(3l, "Bops"));

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
		ResultMatcher checkBody = content()
				.json("[{\"id\":5,\"name\":\"R&B\",\"description\":\"Heartbroken people love this\",\"albums\":[]}]");
		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);
	}
}
