package com.example.service;

import com.example.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Author save(Author author);
    Optional<Author> findById(Long id);
    List<Author> findAll();
    Optional<Author> findByFirstNameAndLastName(String firstName, String lastName);
    void updateById(Author author, Long id);
    void deleteById(Long id);
}
