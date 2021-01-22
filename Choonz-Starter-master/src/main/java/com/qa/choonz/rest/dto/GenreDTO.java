package com.qa.choonz.rest.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class GenreDTO {

	private Long id;
	private String name;
	private String description;
	private List<AlbumDTO> albums;

}
