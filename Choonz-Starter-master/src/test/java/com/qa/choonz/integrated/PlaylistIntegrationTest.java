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
import com.qa.choonz.persistence.domain.Playlist;
import com.qa.choonz.rest.dto.PlaylistDTO;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = { "classpath:task-schema.sql",
		"classpath:testdata.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class PlaylistIntegrationTest {
	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper jsonifier;

	@Autowired
	private ModelMapper mapper;

	private PlaylistDTO mapToDTO(Playlist playlist) {
		return this.mapper.map(playlist, PlaylistDTO.class);
	}

	private final String URI = "/playlist/";

	private final Playlist TEST_1 = new Playlist("The Weeknd", "Best of The Weeknd", "some url");
	private final Playlist TEST_2 = new Playlist(2l, "Pop songs", "best pop songs", "cool artwork");
	private final Playlist TEST_3 = new Playlist(3l, "80s hits", "best 80s songs", "cool artwork");

	@Test
	void createTest() throws Exception {
		PlaylistDTO testDTO = mapToDTO(TEST_1);
		String testDTOAsJSON = jsonifier.writeValueAsString(testDTO);

		RequestBuilder request = post(URI + "create").contentType(MediaType.APPLICATION_JSON).content(testDTOAsJSON);
		ResultMatcher checkStatus = status().isCreated();

		PlaylistDTO testSavedDTO = mapToDTO(TEST_1);
		testSavedDTO.setId(4l);
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
				+ "{\"id\":1,\"name\":\"The Weeknd\",\"description\":\"Best of The Weeknd\",\"artwork\":\"some url\",\"tracks\":[]},"
				+ "{\"id\":2,\"name\":\"Pop songs\",\"description\":\"best pop songs\",\"artwork\":\"cool artwork\",\"tracks\":[]},"
				+ "{\"id\":3,\"name\":\"80s songs\",\"description\":\"best 80s songs\",\"artwork\":\"cool artwork\",\"tracks\":[]}"
				+ "]");
		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);
	}

	@Test
	void updateTest() throws Exception {
		PlaylistDTO testDTO = mapToDTO(new Playlist("Pop songs", "best pop songs", "cool artwork"));
		String testDTOAsJSON = jsonifier.writeValueAsString(testDTO);

		RequestBuilder request = put(URI + "update/" + TEST_3.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(testDTOAsJSON);
		ResultMatcher checkStatus = status().isAccepted();

		PlaylistDTO testSavedDTO = mapToDTO(new Playlist(3l, "Pop songs"));

		ResultMatcher checkBody = jsonPath("name").value(testSavedDTO.getName());

		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);
	}

	@Test
	void deleteTest() throws Exception {
		RequestBuilder request = delete(URI + "delete/" + TEST_3.getId());
		ResultMatcher checkStatus = status().isNoContent();

		this.mvc.perform(request).andExpect(checkStatus);
	}

	@Test
	void searchTest() throws Exception {
		RequestBuilder request = get(URI + "search/" + TEST_2.getName()).contentType(MediaType.APPLICATION_JSON);
		ResultMatcher checkStatus = status().isOk();

		ResultMatcher checkBody = content().json(
				"[{\"id\":2,\"name\":\"Pop songs\",\"description\":\"best pop songs\",\"artwork\":\"cool artwork\",\"tracks\":[]}]");
		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);
	}

}
