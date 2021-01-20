package com.qa.choonz.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.qa.choonz.service.AlbumService;
import com.qa.choonz.service.ArtistService;
import com.qa.choonz.service.GenreService;
import com.qa.choonz.service.PlaylistService;
import com.qa.choonz.service.TrackService;

@Controller
@CrossOrigin
public class SearchController {

	private TrackService trackservice;
	private PlaylistService playlistservice;
	private GenreService genreservice;
	private ArtistService artistservice;
	private AlbumService albumservice;

	@GetMapping("/search/{query}")
	public ResponseEntity<List<Object>> search(@PathVariable String query) {

		List<Object> combined = new ArrayList<Object>();
		combined.addAll(trackservice.search(query));
		combined.addAll(playlistservice.search(query));
		combined.addAll(genreservice.search(query));
		combined.addAll(artistservice.search(query));
		combined.addAll(albumservice.search(query));

		return new ResponseEntity<List<Object>>(combined, HttpStatus.OK);

	}

}
