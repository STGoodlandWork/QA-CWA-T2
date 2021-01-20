package com.qa.choonz.rest.dto;

import java.util.List;

import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.domain.Genre;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class AlbumDTO {

	private long id;
	private String name;
	private List<TrackDTO> tracks;
	private Artist artist;
	private Genre genre;
	private String cover;

}
