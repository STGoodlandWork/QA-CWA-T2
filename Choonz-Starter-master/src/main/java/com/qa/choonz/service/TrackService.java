package com.qa.choonz.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.choonz.exception.TrackNotFoundException;
import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.persistence.repository.TrackRepository;
import com.qa.choonz.rest.dto.TrackDTO;
import com.qa.choonz.util.SpringBeanUtil;

@Service
public class TrackService {

	private TrackRepository repo;
	private ModelMapper mapper;

	@Autowired
	public TrackService(TrackRepository repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}

	private TrackDTO mapToDTO(Track track) {
		return this.mapper.map(track, TrackDTO.class);
	}

	// Create
	public TrackDTO create(Track track) {
		Track created = this.repo.save(track);
		return this.mapToDTO(created);
	}

	// Read All
	public List<TrackDTO> read() {
		return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	// Read By ID
	public TrackDTO read(long id) {
		Track found = this.repo.findById(id).orElseThrow(TrackNotFoundException::new);
		return this.mapToDTO(found);
	}

	// Update
	public TrackDTO update(TrackDTO track, long id) {
		Track toUpdate = this.repo.findById(id).orElseThrow(TrackNotFoundException::new);
		toUpdate.setTitle(track.getTitle());
		toUpdate.setArtist(track.getArtist());
		toUpdate.setAlbum(track.getAlbum());
		toUpdate.setDuration(track.getDuration());
		toUpdate.setLyrics(track.getLyrics());
		toUpdate.setPlaylist(track.getPlaylist());
		SpringBeanUtil.mergeNotNull(track, toUpdate);
		Track updated = this.repo.save(toUpdate);
		return this.mapToDTO(updated);
	}

	// Delete
	public boolean delete(long id) {
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}

	public List<TrackDTO> search(String query) {
		return this.repo.search(query).stream().map(this::mapToDTO).collect(Collectors.toList());
	}

}
