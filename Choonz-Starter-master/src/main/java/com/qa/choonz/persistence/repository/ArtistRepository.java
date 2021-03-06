package com.qa.choonz.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.qa.choonz.persistence.domain.Artist;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

	@Query(value = "select * from artist where name=?1", nativeQuery= true)
	List<Artist> search(String query);
	
}
