package com.qa.choonz.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.choonz.exception.ArtistNotFoundException;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.repository.ArtistRepository;
import com.qa.choonz.rest.dto.ArtistDTO;

@Service
public class ArtistService {

	private ArtistRepository repo;
	private ModelMapper mapper;

	@Autowired
	public ArtistService(ArtistRepository repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}

	private ArtistDTO mapToDTO(Artist artist) {
		return this.mapper.map(artist, ArtistDTO.class);
	}

	// Create
	public ArtistDTO create(Artist artist) {
		return this.mapToDTO(this.repo.save(artist));
	}

	// Read All
	public List<ArtistDTO> read() {
		return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	// Read by ID
	public ArtistDTO read(long id) {
		Artist found = this.repo.findById(id).orElseThrow(ArtistNotFoundException::new);
		return this.mapToDTO(found);
	}

	// Update
	public ArtistDTO update(Artist artist, long id) {
		Artist toUpdate = this.repo.findById(id).orElseThrow(ArtistNotFoundException::new);
		toUpdate.setName(artist.getName());
		toUpdate.setAlbums(artist.getAlbums());
		return this.mapToDTO(this.repo.save(toUpdate));
	}

	// Delete
	public boolean delete(long id) {
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}
	
	public List<ArtistDTO> search(String query) {
    	return this.repo.search(query).stream().map(this::mapToDTO).collect(Collectors.toList());
    }
	
	
}
