package com.qa.choonz.persistence.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Track {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull
	@Size(max = 100)
	@Column(unique = true)
	private String title;

	@OneToOne
	private Artist artist;

	@OneToOne
	private Genre genre;

	@ManyToOne
	private Album album;

	@ManyToOne
	private Playlist playlist;

	// in seconds
	private int duration;

	private String lyrics;

	public Track(long id, @NotNull @Size(max = 100) String title, Artist artist, Album album, Playlist playlist,
			int duration, String lyrics, Genre genre) {
		super();
		this.id = id;
		this.title = title;
		this.album = album;
		this.playlist = playlist;
		this.duration = duration;
		this.lyrics = lyrics;
		this.artist = artist;
		this.genre = genre;
	}

}
