package com.qa.choonz.integrated;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

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
import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.rest.dto.AlbumDTO;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = { "classpath:task-schema.sql",
		"classpath:testdata.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class AlbumIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper jsonifier;

	@Autowired
	private ModelMapper mapper;

	private AlbumDTO mapToDTO(Album album) {
		return this.mapper.map(album, AlbumDTO.class);
	}

	ResultMatcher checkStatus;
	RequestBuilder request;

	private final String URI = "/album/";

	private final Album TEST_1 = new Album("We shall all be healed");
	private final Album TEST_2 = new Album(2l, "Tallahassee");
	private final Album TEST_3 = new Album(3l, "In League With Dragons");
	private final Album TEST_4 = new Album(4l, "The Sunset Tree");

	@Test
	void createTest() throws Exception {
		AlbumDTO testDTO = mapToDTO(TEST_1);
		String testDTOAsJSON = jsonifier.writeValueAsString(testDTO);

		RequestBuilder request = post(URI + "create").contentType(MediaType.APPLICATION_JSON).content(testDTOAsJSON);
		ResultMatcher checkStatus = status().isCreated();

		AlbumDTO testSavedDTO = mapToDTO(TEST_1);
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

		ResultMatcher checkBody = content().json(
				"[" + "{" + "\"id\":1," + "\"name\":\"We Shall All Be Healed\"," + "\"tracks\":[]," + "\"artist\":null,"
						+ "\"genre\":null," + "\"cover\":null}," + "{\"id\":2,\"name\":\"Tallahassee\","
						+ "\"tracks\":[]," + "\"artist\":null," + "\"genre\":null," + "\"cover\":null" + "},"

						+ "{" + "\"id\":3," + "\"name\":\"In League With Dragons\"," + "\"tracks\":[],\"artist\":null,"
						+ "\"genre\":null,\"cover\":null" + "}," + "{" + "\"id\":4," + "\"name\":\"The Sunset Tree\","
						+ "\"tracks\":[]," + "\"artist\":null," + "\"genre\":null," + "\"cover\":null" + "}," + "{"
						+ "\"id\":5," + "\"name\":\"These Four Walls\"," + "\"tracks\":[]," + "\"artist\":null,"
						+ "\"genre\":null," + "\"cover\":null" + "}]");
		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);
	}

	@Test
	void updateTest() throws Exception {
		List<Track> track = null;
		AlbumDTO testDTO = mapToDTO(new Album("In League With Snails", track, null, null, null));
		String testDTOAsJSON = jsonifier.writeValueAsString(testDTO);

		RequestBuilder request = put(URI + "update/" + TEST_3.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(testDTOAsJSON);
		ResultMatcher checkStatus = status().isAccepted();

		AlbumDTO testSavedDTO = mapToDTO(new Album(3l, "In League With Snails"));

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
		RequestBuilder request = get(URI + "search/" + TEST_2.getName()).contentType(MediaType.APPLICATION_JSON);
		ResultMatcher checkStatus = status().isOk();

		ResultMatcher checkBody = content().json(
				"[{\"id\":2,\"name\":\"Tallahassee\",\"tracks\":[],\"artist\":null,\"genre\":null,\"cover\":null}]");
		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);
	}

}
