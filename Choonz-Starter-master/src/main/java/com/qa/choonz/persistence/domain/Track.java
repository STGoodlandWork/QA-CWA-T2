package com.qa.choonz.persistence.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
	private String name;

	@ManyToOne
	private Album album;

	@ManyToOne
	private Playlist playlist;

	// in seconds
	private int duration;

	private String lyrics;

	public Track(long id, @NotNull @Size(max = 100) String name, Album album, Playlist playlist, int duration,
			String lyrics) {
		super();
		this.id = id;
		this.name = name;
		this.album = album;
		this.playlist = playlist;
		this.duration = duration;
		this.lyrics = lyrics;
	}

}
