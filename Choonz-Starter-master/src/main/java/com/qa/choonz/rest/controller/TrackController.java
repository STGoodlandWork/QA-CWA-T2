package com.qa.choonz.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.choonz.persistence.domain.Track;
import com.qa.choonz.rest.dto.TrackDTO;
import com.qa.choonz.service.TrackService;

@RestController
@RequestMapping("/track")
@CrossOrigin
public class TrackController {

	private TrackService service;

	@Autowired
	public TrackController(TrackService service) {
		super();
		this.service = service;
	}

	@PostMapping("/create")
	public ResponseEntity<TrackDTO> create(@RequestBody Track track) {
		return new ResponseEntity<>(this.service.create(track), HttpStatus.CREATED);
	}

	@GetMapping("/read")
	public ResponseEntity<List<TrackDTO>> read() {
		return new ResponseEntity<>(this.service.read(), HttpStatus.OK);
	}

	@GetMapping("/read/{id}")
	public ResponseEntity<TrackDTO> read(@PathVariable long id) {
		return new ResponseEntity<>(this.service.read(id), HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<TrackDTO> update(@RequestBody TrackDTO track, @PathVariable long id) {
		return new ResponseEntity<>(this.service.update(track, id), HttpStatus.ACCEPTED);
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<TrackDTO> delete(@PathVariable long id) {
		return this.service.delete(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("/search/{query}")
	public ResponseEntity<List<TrackDTO>> search(@PathVariable String query) {
		return new ResponseEntity<>(this.service.search(query), HttpStatus.OK);
	}
}
