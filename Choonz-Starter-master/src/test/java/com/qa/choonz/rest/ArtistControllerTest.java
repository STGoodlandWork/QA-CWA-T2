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

import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.rest.controller.ArtistController;
import com.qa.choonz.rest.dto.ArtistDTO;
import com.qa.choonz.service.ArtistService;

@SpringBootTest
public class ArtistControllerTest {

	@Autowired
	private ArtistController controller;

	@MockBean
	private ArtistService service;

	@Autowired
	private ModelMapper mapper;

	private ArtistDTO mapToDTO(Artist artist) {
		return this.mapper.map(artist, ArtistDTO.class);
	}

	private final Artist TEST_1 = new Artist(1l, "The Weeknd", null);
	private final Artist TEST_2 = new Artist(2l, "Daniel Caesar", null);
	private final Artist TEST_3 = new Artist(3l, "SZA", null);
	private final Artist TEST_4 = new Artist(4l, "Brent Faiyaz", null);
	private final Artist TEST_5 = new Artist(5l, "Brent Faiyaz", null);

	private List<Artist> LISTOFARTIST;

	@BeforeEach
	void init() {
		LISTOFARTIST = List.of(TEST_1, TEST_2, TEST_3, TEST_4, TEST_5);
	}

	// Create
	@Test
	void createTest() throws Exception {
		when(this.service.create(TEST_1)).thenReturn(this.mapToDTO(TEST_1));
		assertThat(new ResponseEntity<ArtistDTO>(this.mapToDTO(TEST_1), HttpStatus.CREATED))
				.isEqualTo(this.controller.create(TEST_1));
		verify(this.service, atLeastOnce()).create(TEST_1);
	}

	// Read All
	@Test
	void readAllTest() throws Exception {
		List<ArtistDTO> dtos = LISTOFARTIST.stream().map(this::mapToDTO).collect(Collectors.toList());
		when(this.service.read()).thenReturn(dtos);
		assertThat(this.controller.read()).isEqualTo(new ResponseEntity<>(dtos, HttpStatus.OK));
	}

	// Read by ID
	@Test
	void readByIDTest() throws Exception {
		when(this.service.read(TEST_3.getId())).thenReturn(this.mapToDTO(TEST_3));
		assertThat(new ResponseEntity<ArtistDTO>(this.mapToDTO(TEST_3), HttpStatus.OK))
				.isEqualTo(this.controller.read(TEST_3.getId()));
		verify(this.service, atLeastOnce()).read(TEST_3.getId());
	}

	// Update
	@Test
	void updateTest() throws Exception {
		when(this.service.update(this.mapToDTO(TEST_2), TEST_2.getId())).thenReturn(this.mapToDTO(TEST_2));
		assertThat(new ResponseEntity<ArtistDTO>(this.mapToDTO(TEST_2), HttpStatus.ACCEPTED))
				.isEqualTo(this.controller.update(this.mapToDTO(TEST_2), TEST_2.getId()));
	}

	// Delete
	@Test
	void deleteTest() throws Exception {
		when(this.service.delete(TEST_4.getId())).thenReturn(true);
		assertThat(new ResponseEntity<ArtistDTO>(HttpStatus.NO_CONTENT))
				.isEqualTo(this.controller.delete(TEST_4.getId()));
	}

	// Delete Test
	@Test
	void deleteTestNoEmployee() throws Exception {
		when(this.service.delete(7l)).thenReturn(false);
		assertThat(new ResponseEntity<ArtistDTO>(HttpStatus.INTERNAL_SERVER_ERROR))
				.isEqualTo(this.controller.delete(7l));
	}

	// Search Test
	@Test
	void deleteSearchTest() throws Exception {
		String query = "The Weeknd";
		when(this.service.search(query)).thenReturn(List.of(mapToDTO(TEST_1)));
		assertThat(new ResponseEntity<List<ArtistDTO>>(this.service.search(query), HttpStatus.OK))
				.isEqualTo(this.controller.search(query));
	}
}
