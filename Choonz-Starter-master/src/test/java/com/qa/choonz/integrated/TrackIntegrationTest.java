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
import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.rest.dto.TrackDTO;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = { "classpath:task-schema.sql",
		"classpath:testdata.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class TrackIntegrationTest {
	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper jsonifier;

	@Autowired
	private ModelMapper mapper;

	private TrackDTO mapToDTO(Track track) {
		return this.mapper.map(track, TrackDTO.class);
	}

	private final String URI = "/track/";

	private final Track TEST_1 = new Track("Good Days", 120, "Laurem Epsom");
	private final Track TEST_2 = new Track(2l, "We Hate Git", null, null, null, null, 120, "Laurem Epsom");
	private final Track TEST_3 = new Track(3l, "Git GUI's FTW", null, null, null, null, 120, "Laurem Epsom");
	private final Track TEST_4 = new Track(4l, "git push -f saved my life", null, null, null, null, 120,
			"Laurem Epsom");

	@Test
	void createTest() throws Exception {
		TrackDTO testDTO = mapToDTO(TEST_1);
		String testDTOAsJSON = jsonifier.writeValueAsString(testDTO);

		RequestBuilder request = post(URI + "create").contentType(MediaType.APPLICATION_JSON).content(testDTOAsJSON);
		ResultMatcher checkStatus = status().isCreated();

		TrackDTO testSavedDTO = mapToDTO(TEST_1);
		testSavedDTO.setId(5l);
		String testSavedDTOAsJSON = jsonifier.writeValueAsString(testSavedDTO);

		ResultMatcher checkBody = content().json(testSavedDTOAsJSON);

		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);
	}

	@Test
	void readByIDTest() throws Exception {
		RequestBuilder request = get(URI + "read/" + TEST_2.getId());
		ResultMatcher checkStatus = status().isOk();
		ResultMatcher checkContent = content().contentType(MediaType.APPLICATION_JSON);
		ResultMatcher checkBody = jsonPath("title").value(TEST_2.getTitle());

		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkContent).andExpect(checkBody);
	}

	@Test
	void readAllTest() throws Exception {
		RequestBuilder request = get(URI + "read").contentType(MediaType.APPLICATION_JSON);
		ResultMatcher checkStatus = status().isOk();
		ResultMatcher checkBody = content().json("["
				+ "{\"id\":1,\"title\":\"Good Days\",\"artist\":null,\"genre\":null,\"album\":null,\"playlist\":null,\"duration\":120,\"lyrics\":\"Laurem Epsom\"},"
				+ "{\"id\":2,\"title\":\"We Hate Git\",\"artist\":null,\"genre\":null,\"album\":null,\"playlist\":null,\"duration\":120,\"lyrics\":\"Laurem Epsom\"},"
				+ "{\"id\":3,\"title\":\"Ringtone\",\"artist\":null,\"genre\":null,\"album\":null,\"playlist\":null,\"duration\":120,\"lyrics\":\"Laurem Epsom\"},"
				+ "{\"id\":4,\"title\":\"git push -f saved my life\",\"artist\":null,\"genre\":null,\"album\":null,\"playlist\":null,\"duration\":120,\"lyrics\":\"Laurem Epsom\"}"
				+ "]");

		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);
	}

	@Test
	void updateTest() throws Exception {
		TrackDTO testDTO = mapToDTO(new Track("Beautiful Girls"));
		String testDTOAsJSON = jsonifier.writeValueAsString(testDTO);

		RequestBuilder request = put(URI + "update/" + TEST_3.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(testDTOAsJSON);
		ResultMatcher checkStatus = status().isAccepted();

		TrackDTO testSavedDTO = mapToDTO(new Track(3l, "Beautiful Girls"));

		ResultMatcher checkBody = jsonPath("title").value(testSavedDTO.getTitle());

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
		RequestBuilder request = get(URI + "search/" + TEST_4.getTitle()).contentType(MediaType.APPLICATION_JSON);
		ResultMatcher checkStatus = status().isOk();

		ResultMatcher checkBody = content().json(
				"[{\"id\":4,\"title\":\"git push -f saved my life\",\"artist\":null,\"genre\":null,\"album\":null,\"playlist\":null,\"duration\":120,\"lyrics\":\"Laurem Epsom\"}]");
		this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);
	}
}
