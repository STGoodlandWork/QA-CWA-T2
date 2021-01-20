package com.qa.choonz.persistence.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Album {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull
	@Size(max = 100)
	@Column(unique = true)
	private String name;

	@OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Track> tracks;

	@ManyToOne
	private Artist artist;

	@ManyToOne
	private Genre genre;

	private String cover;

	public Album(long id, @NotNull @Size(max = 100) String name, List<Track> tracks, Artist artist, Genre genre,
			String cover) {
		super();
		this.id = id;
		this.name = name;
		this.tracks = tracks;
		this.artist = artist;
		this.genre = genre;
		this.cover = cover;
	}

}
