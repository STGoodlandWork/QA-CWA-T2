package com.qa.choonz.rest.dto;

import java.util.List;

import com.qa.choonz.persistence.domain.Album;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArtistDTO {

	private long id;
	private String name;
	private List<Album> albums;

}
