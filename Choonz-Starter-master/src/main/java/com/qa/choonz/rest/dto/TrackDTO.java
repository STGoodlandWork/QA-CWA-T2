package com.qa.choonz.rest.dto;

import com.qa.choonz.persistence.domain.Album;
import com.qa.choonz.persistence.domain.Artist;
import com.qa.choonz.persistence.domain.Genre;
import com.qa.choonz.persistence.domain.Playlist;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TrackDTO {

	private Long id;
	private String title;
	private Artist artist;
	private Genre genre;
	private Album album;
	private Playlist playlist;
	private int duration;
	private String lyrics;

}
