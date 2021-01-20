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

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Artist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull
	@Size(max = 100)
	@Column(unique = true)
	private String name;

	@OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Album> albums;

	@OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Track> tracks;

	public Artist(long id, @NotNull @Size(max = 100) String name, List<Album> albums) {
		super();
		this.id = id;
		this.name = name;
		this.albums = albums;
	}

}
