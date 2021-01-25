package com.qa.choonz.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.repository.ArtistRepository;
import com.qa.choonz.rest.dto.ArtistDTO;
import com.qa.choonz.util.SpringBeanUtil;

@SpringBootTest
class ArtistServiceTest {

	@MockBean
	private ArtistRepository repo;

	@MockBean
	private SpringBeanUtil util;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private ArtistService service;

	private ArtistDTO mapToDTO(Artist artist) {
		return this.mapper.map(artist, ArtistDTO.class);
	}

	private final Artist TEST_1 = new Artist(1l, "The Weeknd", null);
	private final Artist TEST_2 = new Artist(2l, "Daniel Caesar", null);
	private final Artist TEST_3 = new Artist(3l, "SZA", null);
	private final Artist TEST_4 = new Artist(4l, "Brent Faiyaz", null);
	private final Artist TEST_5 = new Artist(5l, "Brent Faiyaz", null);

	private List<ArtistDTO> LISTOFARTIST;

	@BeforeEach
	void init() {
		LISTOFARTIST = List.of(mapToDTO(TEST_1), mapToDTO(TEST_2), mapToDTO(TEST_3), mapToDTO(TEST_4),
				mapToDTO(TEST_5));
	}

	@Test
	void createTest() throws Exception {
		when(repo.save(TEST_1)).thenReturn(TEST_1);
		assertThat(service.create(TEST_1)).isEqualTo(this.mapToDTO(TEST_1));
		verify(repo, atLeastOnce()).save(TEST_1);
	}

	@Test
	void readAllTest() throws Exception {
		when(repo.findAll()).thenReturn(List.of(TEST_1, TEST_2, TEST_3, TEST_4, TEST_5));
		assertThat(service.read()).isEqualTo(LISTOFARTIST);
		verify(repo, atLeastOnce()).findAll();
	}

	@Test
	void readByID() throws Exception {
		when(repo.findById(TEST_3.getId())).thenReturn(Optional.of(TEST_3));
		assertThat(service.read(TEST_3.getId())).isEqualTo(mapToDTO(TEST_3));
		verify(repo, atLeastOnce()).findById(TEST_3.getId());
	}

	@Test
	void updateTest() throws Exception {
		Artist newNameTEST_4 = TEST_4;
		newNameTEST_4.setName("Rich Brian");
		ArtistDTO expectedDTO = mapToDTO(newNameTEST_4);
		when(repo.findById(TEST_4.getId())).thenReturn(Optional.of(TEST_4));
		when(repo.save(newNameTEST_4)).thenReturn(newNameTEST_4);
		assertThat(service.update(mapToDTO(TEST_4), TEST_4.getId())).isEqualTo(expectedDTO);
		verify(repo, atLeastOnce()).findById(TEST_4.getId());
		verify(repo, atLeastOnce()).save(newNameTEST_4);
	}

	@Test
	void deleteTest() throws Exception {
		when(repo.existsById(TEST_5.getId())).thenReturn(false);
		assertThat(service.delete(TEST_5.getId())).isTrue();
		verify(repo, atLeastOnce()).deleteById(TEST_5.getId());
		verify(repo, atLeastOnce()).existsById(TEST_5.getId());
	}

	@Test
	void deleteFailureTest() throws Exception {
		Long id = 1L;
		when(this.repo.existsById(id)).thenReturn(true);
		assertThat(this.service.delete(id)).isFalse();
		verify(this.repo, atLeastOnce()).existsById(id);
	}

	@Test
	void searchTest() throws Exception {
		String query = "The Weeknd";
		when(repo.search(query)).thenReturn(List.of(TEST_1));
		assertThat(service.search(query)).isEqualTo(List.of(mapToDTO(TEST_1)));
		verify(repo, atLeastOnce()).search(query);
	}
}
