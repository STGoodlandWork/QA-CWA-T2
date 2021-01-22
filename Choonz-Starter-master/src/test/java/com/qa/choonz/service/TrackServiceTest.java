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

import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.persistence.repository.TrackRepository;
import com.qa.choonz.rest.dto.TrackDTO;
import com.qa.choonz.utils.BeanUtils;

@SpringBootTest
public class TrackServiceTest {

	@MockBean
	private TrackRepository repo;

	@MockBean
	private BeanUtils util;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private TrackService service;

	private TrackDTO mapToDTO(Track track) {
		return this.mapper.map(track, TrackDTO.class);
	}

	private final Track TEST_1 = new Track(1l, "Good Days", null, null, null, 120, "Laurem Epsom", null);
	private final Track TEST_2 = new Track(2l, "We Hate Git", null, null, null, 120, "Laurem Epsom", null);
	private final Track TEST_3 = new Track(3l, "Git GUI's FTW", null, null, null, 120, "Laurem Epsom", null);
	private final Track TEST_4 = new Track(4l, "git push -f saved my life", null, null, null, 120, "Laurem Epsom",
			null);
	private final Track TEST_5 = new Track(5l, "Great Days", null, null, null, 120, "Laurem Epsom", null);

	private List<TrackDTO> LISTOFTRACKS;

	@BeforeEach
	void init() {
		LISTOFTRACKS = List.of(mapToDTO(TEST_1), mapToDTO(TEST_2), mapToDTO(TEST_3), mapToDTO(TEST_4),
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
		assertThat(service.read()).isEqualTo(LISTOFTRACKS);
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
		Track newNameTEST_4 = TEST_4;
		newNameTEST_4.setTitle("Ringtone");
		TrackDTO expectedDTO = mapToDTO(newNameTEST_4);
		when(repo.findById(TEST_4.getId())).thenReturn(Optional.of(TEST_4));
		when(repo.save(newNameTEST_4)).thenReturn(newNameTEST_4);
		assertThat(service.update(TEST_4, TEST_4.getId())).isEqualTo(expectedDTO);
		verify(repo, atLeastOnce()).findById(TEST_4.getId());
		verify(repo, atLeastOnce()).save(newNameTEST_4);
	}

	@Test
	void deleteTest() throws Exception {
		when(repo.existsById(TEST_5.getId())).thenReturn(false);
		assertThat(service.delete(TEST_5.getId())).isEqualTo(true);
		verify(repo, atLeastOnce()).deleteById(TEST_5.getId());
		verify(repo, atLeastOnce()).existsById(TEST_5.getId());
	}

	@Test
	void searchTest() throws Exception {
		String query = "Good Days";
		when(repo.search(query)).thenReturn(List.of(TEST_1));
		assertThat(service.search(query)).isEqualTo(List.of(mapToDTO(TEST_1)));
		verify(repo, atLeastOnce()).search(query);
	}

}
