package com.qa.choonz.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.rest.controller.TrackController;
import com.qa.choonz.rest.dto.TrackDTO;
import com.qa.choonz.service.TrackService;

@SpringBootTest
class TrackControllerTest {

	@Autowired
	private TrackController controller;

	@MockBean
	private TrackService service;

	@Autowired
	private ModelMapper mapper;

	private TrackDTO mapToDTO(Track track) {
		return this.mapper.map(track, TrackDTO.class);
	}

	private final Track TEST_1 = new Track(1l, "Good Days", null, null, null, null, 120, "Laurem Epsom");
	private final Track TEST_2 = new Track(2l, "We Hate Git", null, null, null, null, 120, "Laurem Epsom");
	private final Track TEST_3 = new Track(3l, "Git GUI's FTW", null, null, null, null, 120, "Laurem Epsom");
	private final Track TEST_4 = new Track(4l, "git push -f saved my life", null, null, null, null, 120,
			"Laurem Epsom");
	private final Track TEST_5 = new Track(5l, "Great Days", null, null, null, null, 120, "Laurem Epsom");

	private List<Track> LISTOFTRACKS;

	@BeforeEach
	void init() {
		LISTOFTRACKS = List.of(TEST_1, TEST_2, TEST_3, TEST_4, TEST_5);
	}

	// Create
	@Test
	void createTest() throws Exception {
		when(this.service.create(TEST_1)).thenReturn(this.mapToDTO(TEST_1));
		assertThat(new ResponseEntity<TrackDTO>(this.mapToDTO(TEST_1), HttpStatus.CREATED))
				.isEqualTo(this.controller.create(TEST_1));
		verify(this.service, atLeastOnce()).create(TEST_1);
	}

	// Read All
	@Test
	void readAllTest() throws Exception {
		List<TrackDTO> dtos = LISTOFTRACKS.stream().map(this::mapToDTO).collect(Collectors.toList());
		when(this.service.read()).thenReturn(dtos);
		assertThat(this.controller.read()).isEqualTo(new ResponseEntity<>(dtos, HttpStatus.OK));
	}

	// Read by ID
	@Test
	void readByIDTest() throws Exception {
		when(this.service.read(TEST_3.getId())).thenReturn(this.mapToDTO(TEST_3));
		assertThat(new ResponseEntity<TrackDTO>(this.mapToDTO(TEST_3), HttpStatus.OK))
				.isEqualTo(this.controller.read(TEST_3.getId()));
		verify(this.service, atLeastOnce()).read(TEST_3.getId());
	}

	// Update
	@Test
	void updateTest() throws Exception {
		when(this.service.update(mapToDTO(TEST_2), TEST_2.getId())).thenReturn(this.mapToDTO(TEST_2));
		assertThat(new ResponseEntity<TrackDTO>(this.mapToDTO(TEST_2), HttpStatus.ACCEPTED))
				.isEqualTo(this.controller.update(mapToDTO(TEST_2), TEST_2.getId()));
	}

	// Delete
	@Test
	void deleteTest() throws Exception {
		when(this.service.delete(TEST_4.getId())).thenReturn(true);
		assertThat(new ResponseEntity<TrackDTO>(HttpStatus.NO_CONTENT))
				.isEqualTo(this.controller.delete(TEST_4.getId()));
	}

	// Delete Test
	@Test
	void deleteTestNoEmployee() throws Exception {
		when(this.service.delete(7l)).thenReturn(false);
		assertThat(new ResponseEntity<TrackDTO>(HttpStatus.INTERNAL_SERVER_ERROR))
				.isEqualTo(this.controller.delete(7l));
	}

	// Search Test
	@Test
	void deleteSearchTest() throws Exception {
		String query = "Good Days";
		when(this.service.search(query)).thenReturn(List.of(mapToDTO(TEST_1)));
		assertThat(new ResponseEntity<List<TrackDTO>>(this.service.search(query), HttpStatus.OK))
				.isEqualTo(this.controller.search(query));
	}
}
