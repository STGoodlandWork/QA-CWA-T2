package com.qa.choonz.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.repository.AlbumRepository;
import com.qa.choonz.rest.dto.AlbumDTO;

@SpringBootTest

public class AlbumServiceTest {

	@Autowired
	private AlbumService service;

	@MockBean
	private AlbumRepository repo;

	@Autowired
	private ModelMapper mapper;

	private AlbumDTO mapToDTO(Album album) {
		return this.mapper.map(album, AlbumDTO.class);
	}

	private final Album test_album = new Album(1l, "We shall all be healed", null, null, null, "some url");
	private final Album test_album2 = new Album(1l, "Tallahassee", null, null, null, "some url");
	private final Album test_album3 = new Album(1l, "In League With Dragons", null, null, null, "some url");
	private final Album test_album4 = new Album(1l, "The Sunset Tree", null, null, null, "some url");
	private final List<Album> LIST_OF_ALBUMS = List.of(test_album, test_album2, test_album3, test_album4);

	@Test
	void createTest() throws Exception {
		Album album = new Album(0l, "We shall all be healed", null, null, null, "some url");
		Album albumFull = new Album(1l, "We shall all be healed", null, null, null, "some url");
		when(this.repo.save(album)).thenReturn(albumFull);
		assertThat(this.service.create(album)).isEqualTo(this.mapToDTO(albumFull));
		verify(this.repo, atLeastOnce()).save(album);
	}

	@Test
	void readAllTest() throws Exception {
		when(this.repo.findAll()).thenReturn(LIST_OF_ALBUMS);

		List<AlbumDTO> list = LIST_OF_ALBUMS.stream().map(this::mapToDTO).collect(Collectors.toList());

		assertThat(this.service.read()).isEqualTo(list);
		verify(this.repo, atLeastOnce()).findAll();
	}

	@Test
	void readOneTest() throws Exception {
		Long id = 1L;
		when(this.repo.findById(id)).thenReturn(Optional.of(test_album));
		assertThat(this.service.read(id)).isEqualTo(this.mapToDTO(test_album));
		verify(this.repo, atLeastOnce()).findById(id);
	}

	@Test
	void updateTest() throws Exception {
		Album updatedAlbum = test_album;
		updatedAlbum.setName("Oww it hurts, heal me");
		AlbumDTO expectedDTO = mapToDTO(updatedAlbum);
		when(repo.findById(test_album.getId())).thenReturn(Optional.of(test_album));
		when(repo.save(updatedAlbum)).thenReturn(updatedAlbum);
		assertThat(service.update(test_album, test_album.getId())).isEqualTo(expectedDTO);
		verify(repo, atLeastOnce()).findById(test_album.getId());
		verify(repo, atLeastOnce()).save(updatedAlbum);

	}

	@Test
	void deleteSuccessTest() throws Exception {
		Long id = 1L;
		when(this.repo.existsById(id)).thenReturn(false);
		assertThat(this.service.delete(id)).isEqualTo(true);
		verify(this.repo, atLeastOnce()).existsById(id);
	}

	@Test
	void deleteFailureTest() throws Exception {
		Long id = 1L;
		when(this.repo.existsById(id)).thenReturn(true);
		assertThat(this.service.delete(id)).isEqualTo(false);
		verify(this.repo, atLeastOnce()).existsById(id);
	}

	@Test
	void searchTest() throws Exception {
		String query = "We shall all be healed";
		when(this.repo.search(query)).thenReturn(List.of(test_album));

		assertThat(this.service.search(query)).isEqualTo(List.of(mapToDTO(test_album)));
		verify(this.repo, atLeastOnce()).search(query);
	}

}
