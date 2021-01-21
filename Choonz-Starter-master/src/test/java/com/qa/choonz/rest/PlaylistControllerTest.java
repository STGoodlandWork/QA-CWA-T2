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

import com.qa.choonz.persistence.domain.Playlist;
import com.qa.choonz.rest.controller.PlaylistController;
import com.qa.choonz.rest.dto.PlaylistDTO;
import com.qa.choonz.service.PlaylistService;

@SpringBootTest
public class PlaylistControllerTest {

	@Autowired
	private PlaylistController controller;

	@MockBean
	private PlaylistService service;

	@Autowired
	private ModelMapper mapper;

	private PlaylistDTO mapToDTO(Playlist playlist) {
		return this.mapper.map(playlist, PlaylistDTO.class);
	}

	private final Playlist TEST_1 = new Playlist(1l, "The Weeknd", "best weekend songs", "cool artwork", null);
	private final Playlist TEST_2 = new Playlist(1l, "Pop songs", "best pop songs", "cool artwork", null);
	private final Playlist TEST_3 = new Playlist(1l, "80s hits", "best 80s songs", "cool artwork", null);
	private final Playlist TEST_4 = new Playlist(1l, "Easy 90s", "best chill 90s songs", "cool artwork", null);
	private final Playlist TEST_5 = new Playlist(1l, "Party classics", "best party songs", "cool artwork", null);

	private List<Playlist> LISTOFPLAYLIST;

	@BeforeEach
	void init() {
		LISTOFPLAYLIST = List.of(TEST_1, TEST_2, TEST_3, TEST_4, TEST_5);
	}

	// Create
	@Test
	void createTest() throws Exception {
		when(this.service.create(TEST_1)).thenReturn(this.mapToDTO(TEST_1));
		assertThat(new ResponseEntity<PlaylistDTO>(this.mapToDTO(TEST_1), HttpStatus.CREATED))
				.isEqualTo(this.controller.create(TEST_1));
		verify(this.service, atLeastOnce()).create(TEST_1);
	}

	// Read All
	@Test
	void readAllTest() throws Exception {
		List<PlaylistDTO> dtos = LISTOFPLAYLIST.stream().map(this::mapToDTO).collect(Collectors.toList());
		when(this.service.read()).thenReturn(dtos);
		assertThat(this.controller.read()).isEqualTo(new ResponseEntity<>(dtos, HttpStatus.OK));
	}

	// Read by ID
	@Test
	void readByIDTest() throws Exception {
		when(this.service.read(TEST_3.getId())).thenReturn(this.mapToDTO(TEST_3));
		assertThat(new ResponseEntity<PlaylistDTO>(this.mapToDTO(TEST_3), HttpStatus.OK))
				.isEqualTo(this.controller.read(TEST_3.getId()));
		verify(this.service, atLeastOnce()).read(TEST_3.getId());
	}

	// Update
	@Test
	void updateTest() throws Exception {
		when(this.service.update(TEST_2, TEST_2.getId())).thenReturn(this.mapToDTO(TEST_2));
		assertThat(new ResponseEntity<PlaylistDTO>(this.mapToDTO(TEST_2), HttpStatus.ACCEPTED))
				.isEqualTo(this.controller.update(TEST_2, TEST_2.getId()));
	}

	// Delete
	@Test
	void deleteTest() throws Exception {
		when(this.service.delete(TEST_4.getId())).thenReturn(true);
		assertThat(new ResponseEntity<PlaylistDTO>(HttpStatus.NO_CONTENT))
				.isEqualTo(this.controller.delete(TEST_4.getId()));
	}

	// Delete Test
	@Test
	void deleteTestNoEmployee() throws Exception {
		when(this.service.delete(7l)).thenReturn(false);
		assertThat(new ResponseEntity<PlaylistDTO>(HttpStatus.INTERNAL_SERVER_ERROR))
				.isEqualTo(this.controller.delete(7l));
	}

	// Search Test
	@Test
	void deleteSearchTest() throws Exception {
		String query = "Good Days";
		when(this.service.search(query)).thenReturn(List.of(mapToDTO(TEST_1)));
		assertThat(new ResponseEntity<List<PlaylistDTO>>(this.service.search(query), HttpStatus.OK))
				.isEqualTo(this.controller.search(query));
	}
}
