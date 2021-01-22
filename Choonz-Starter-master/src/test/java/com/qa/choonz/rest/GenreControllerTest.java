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

import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.rest.controller.GenreController;
import com.qa.choonz.rest.dto.GenreDTO;
import com.qa.choonz.service.GenreService;

@SpringBootTest
public class GenreControllerTest {

	@Autowired
	private GenreController controller;

	@MockBean
	private GenreService service;

	@Autowired
	private ModelMapper mapper;

	private GenreDTO mapToDTO(Genre genre) {
		return this.mapper.map(genre, GenreDTO.class);
	}

	private final Genre TEST_1 = new Genre("Rap", "Rhymthm and Poetry music", null);
	private final Genre TEST_2 = new Genre(2l, "Pop", "Music for the Radio", null);
	private final Genre TEST_3 = new Genre(3l, "Country", "Lil Nas X created this", null);
	private final Genre TEST_4 = new Genre(4l, "House", "Good Vibes Music", null);
	private final Genre TEST_5 = new Genre(5l, "R&B", "Heartbroken people love this", null);

	private List<Genre> LISTOFGENRES;

	@BeforeEach
	void init() {
		LISTOFGENRES = List.of(TEST_1, TEST_2, TEST_3, TEST_4, TEST_5);
	}

	// Create
	@Test
	void createTest() throws Exception {
		when(this.service.create(TEST_1)).thenReturn(this.mapToDTO(TEST_1));
		assertThat(new ResponseEntity<GenreDTO>(this.mapToDTO(TEST_1), HttpStatus.CREATED))
				.isEqualTo(this.controller.create(TEST_1));
		verify(this.service, atLeastOnce()).create(TEST_1);
	}

	// Read All
	@Test
	void readAllTest() throws Exception {
		List<GenreDTO> dtos = LISTOFGENRES.stream().map(this::mapToDTO).collect(Collectors.toList());
		when(this.service.read()).thenReturn(dtos);
		assertThat(this.controller.read()).isEqualTo(new ResponseEntity<>(dtos, HttpStatus.OK));
	}

	// Read by ID
	@Test
	void readByIDTest() throws Exception {
		when(this.service.read(TEST_3.getId())).thenReturn(this.mapToDTO(TEST_3));
		assertThat(new ResponseEntity<GenreDTO>(this.mapToDTO(TEST_3), HttpStatus.OK))
				.isEqualTo(this.controller.read(TEST_3.getId()));
		verify(this.service, atLeastOnce()).read(TEST_3.getId());
	}

	// Update
	@Test
	void updateTest() throws Exception {
		when(this.service.update(this.mapToDTO(TEST_2), TEST_2.getId())).thenReturn(this.mapToDTO(TEST_2));
		assertThat(new ResponseEntity<GenreDTO>(this.mapToDTO(TEST_2), HttpStatus.ACCEPTED))
				.isEqualTo(this.controller.update(this.mapToDTO(TEST_2), TEST_2.getId()));
	}

	// Delete
	@Test
	void deleteTest() throws Exception {
		when(this.service.delete(TEST_4.getId())).thenReturn(true);
		assertThat(new ResponseEntity<GenreDTO>(HttpStatus.NO_CONTENT))
				.isEqualTo(this.controller.delete(TEST_4.getId()));
	}

	// Delete Test
	@Test
	void deleteTestNoEmployee() throws Exception {
		when(this.service.delete(7l)).thenReturn(false);
		assertThat(new ResponseEntity<GenreDTO>(HttpStatus.INTERNAL_SERVER_ERROR))
				.isEqualTo(this.controller.delete(7l));
	}

	// Search Test
	@Test
	void deleteSearchTest() throws Exception {
		String query = "Rap";
		when(this.service.search(query)).thenReturn(List.of(mapToDTO(TEST_1)));
		assertThat(new ResponseEntity<List<GenreDTO>>(this.service.search(query), HttpStatus.OK))
				.isEqualTo(this.controller.search(query));
	}

}
