package com.example.repository;

import com.example.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    @Query("select g from Genre g where g.genreName = :genre")
    Optional<Genre> findByGenre(@Param("genre") String genre);
}
