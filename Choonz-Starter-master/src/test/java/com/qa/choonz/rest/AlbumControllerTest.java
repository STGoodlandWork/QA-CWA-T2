package com.qa.choonz.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.rest.controller.AlbumController;
import com.qa.choonz.rest.dto.AlbumDTO;
import com.qa.choonz.rest.dto.ArtistDTO;
import com.qa.choonz.service.AlbumService;

@SpringBootTest
class AlbumControllerTest {

	@Autowired
	private AlbumController controller;

	@MockBean
	private AlbumService service;

	@Autowired
	private ModelMapper mapper;

	private AlbumDTO mapToDTO(Album album) {
		return this.mapper.map(album, AlbumDTO.class);
	}

	private final Album TEST_1 = new Album(1l, "We shall all be healed", null, null, null, "some url");
	private final Album TEST_2 = new Album(1l, "Tallahassee", null, null, null, "some url");
	private final Album TEST_3 = new Album(1l, "In League With Dragons", null, null, null, "some url");
	private final Album TEST_4 = new Album(1l, "The Sunset Tree", null, null, null, "some url");

	private final List<Album> LIST_OF_ALBUMS = List.of(TEST_1, TEST_2, TEST_3, TEST_4);

	// Create
	@Test
	void createTest() throws Exception {
		when(this.service.create(TEST_1)).thenReturn(this.mapToDTO(TEST_1));
		assertThat(new ResponseEntity<AlbumDTO>(this.service.create(TEST_1), HttpStatus.CREATED))
				.isEqualTo(this.controller.create(TEST_1));
		verify(this.service, atLeastOnce()).create(TEST_1);
	}

	// Read All
	@Test
	void readAllTest() throws Exception {
		List<AlbumDTO> dtos = LIST_OF_ALBUMS.stream().map(this::mapToDTO).collect(Collectors.toList());
		when(this.service.read()).thenReturn(dtos);
		assertThat(this.controller.read()).isEqualTo(new ResponseEntity<>(dtos, HttpStatus.OK));
	}

	// Read by ID
	@Test
	void readByIDTest() throws Exception {
		when(this.service.read(TEST_3.getId())).thenReturn(this.mapToDTO(TEST_3));
		assertThat(new ResponseEntity<AlbumDTO>(this.mapToDTO(TEST_3), HttpStatus.OK))
				.isEqualTo(this.controller.read(TEST_3.getId()));
		verify(this.service, atLeastOnce()).read(TEST_3.getId());
	}

	// Update
	@Test
	void updateTest() throws Exception {
		when(this.service.update(mapToDTO(TEST_2), TEST_2.getId())).thenReturn(this.mapToDTO(TEST_2));
		assertThat(new ResponseEntity<AlbumDTO>(this.mapToDTO(TEST_2), HttpStatus.ACCEPTED))
				.isEqualTo(this.controller.update(mapToDTO(TEST_2), TEST_2.getId()));
	}

	// Delete
	@Test
	void deleteTest() throws Exception {
		when(this.service.delete(TEST_4.getId())).thenReturn(true);
		assertThat(new ResponseEntity<AlbumDTO>(HttpStatus.NO_CONTENT))
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
		String query = "We shall all be healed";
		when(this.service.search(query)).thenReturn(List.of(mapToDTO(TEST_1)));
		assertThat(new ResponseEntity<List<AlbumDTO>>(this.service.search(query), HttpStatus.OK))
				.isEqualTo(this.controller.search(query));
	}
}
