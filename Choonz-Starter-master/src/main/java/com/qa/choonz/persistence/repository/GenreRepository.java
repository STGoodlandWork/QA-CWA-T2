package com.qa.choonz.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.qa.choonz.persistence.domain.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
	@Query(value = "select * from Genre where name=?1", nativeQuery= true)
	List<Genre> search(String query);

}
