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
public class Genre {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(max = 100)
	@Column(unique = true)
	private String name;

	@Size(max = 250)
	@Column(unique = true)
	private String description;

	@OneToMany(mappedBy = "genre", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Album> albums;

	public Genre(Long id, @NotNull @Size(max = 100) String name, @NotNull @Size(max = 250) String description,
			List<Album> albums) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.albums = albums;
	}

	public Genre(@NotNull @Size(max = 100) String name, @NotNull @Size(max = 250) String description,
			List<Album> albums) {
		super();
		this.name = name;
		this.description = description;
		this.albums = albums;
	}

	public Genre(@NotNull @Size(max = 100) String name) {
		super();
		this.name = name;
	}

	public Genre(Long id, @NotNull @Size(max = 100) String name) {
		super();
		this.id = id;
		this.name = name;
	}
}
