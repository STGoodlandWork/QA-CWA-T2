package com.qa.choonz.persistence.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Playlist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(max = 100)
	@Column(unique = true)
	private String name;

	@NotNull
	@Size(max = 500)
	@Column(unique = true)
	private String description;

	@NotNull
	@Size(max = 1000)
	@Column(unique = true)
	private String artwork;

	@OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Track> tracks;

	public Playlist(long id, @NotNull @Size(max = 100) String name, @NotNull @Size(max = 500) String description,
			@NotNull @Size(max = 1000) String artwork, List<Track> tracks) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.artwork = artwork;
		this.tracks = tracks;
	}

	public Playlist(Long id, @NotNull @Size(max = 100) String name, @NotNull @Size(max = 500) String description,
			@NotNull @Size(max = 1000) String artwork) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.artwork = artwork;
	}

	public Playlist(@NotNull @Size(max = 100) String name, @NotNull @Size(max = 500) String description,
			@NotNull @Size(max = 1000) String artwork) {
		super();
		this.name = name;
		this.description = description;
		this.artwork = artwork;
	}

	public Playlist(Long id, @NotNull @Size(max = 100) String name) {
		super();
		this.id = id;
		this.name = name;
	}

}
