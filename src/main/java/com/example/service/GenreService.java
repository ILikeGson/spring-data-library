package com.example.service;

import com.example.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    Genre save(Genre genre);
    Genre findById(Long id);
    List<Genre> findAll();
    Optional<Genre>  findByGenre(String genre);
    boolean existsById(Long id);
    void updateById(Genre genre, Long id);
    void deleteById(Long id);
}
