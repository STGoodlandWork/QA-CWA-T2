package com.qa.choonz.rest.controller;
package com.qa.choonz.rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
import com.qa.choonz.service.PlaylistService;
import com.qa.choonz.service.TrackService;

@Controller
@CrossOrigin
public class SearchController {

	private TrackService trackservice;
	private PlaylistService playlistservice;
	// private GenreController genrecontroller;
	// private ArtistController artistcontroller;
	// private AlbumController albumcontroller;

	@GetMapping("/search/{query}")
	public ResponseEntity<List<Object>> search(@PathVariable String query) {

		List<Object> combined = new ArrayList();
		combined.addAll(trackservice.search(query));
		combined.addAll(playlistservice.search(query));

		return new ResponseEntity<List<Object>>(combined, HttpStatus.OK);

	}

}
