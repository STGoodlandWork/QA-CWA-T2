package com.qa.choonz.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.qa.choonz.exception.AlbumNotFoundException;
import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.repository.AlbumRepository;
import com.qa.choonz.rest.dto.AlbumDTO;
import com.qa.choonz.util.SpringBeanUtil;

@Service
public class AlbumService {

	private AlbumRepository repo;
	private ModelMapper mapper;

	public AlbumService(AlbumRepository repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}

	private AlbumDTO mapToDTO(Album album) {
		return this.mapper.map(album, AlbumDTO.class);
	}

	public AlbumDTO create(Album album) {
		return this.mapToDTO(this.repo.save(album));
	}

	public List<AlbumDTO> read() {
		return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	public AlbumDTO read(long id) {
		Album found = this.repo.findById(id).orElseThrow(AlbumNotFoundException::new);
		return this.mapToDTO(found);
	}

	public AlbumDTO update(AlbumDTO album, Long id) {
		Album toUpdate = this.repo.findById(id).orElseThrow(AlbumNotFoundException::new);
		toUpdate.setName(album.getName());
		toUpdate.setArtist(album.getArtist());
		toUpdate.setCover(album.getCover());
		Album updated = this.repo.save(toUpdate);
		SpringBeanUtil.mergeNotNull(album, toUpdate);
		return this.mapToDTO(updated);
	}

	public boolean delete(long id) {
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}

	public List<AlbumDTO> search(String query) {
		return this.repo.search(query).stream().map(this::mapToDTO).collect(Collectors.toList());
	}

}
