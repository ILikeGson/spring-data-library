package com.example.service;

import com.example.model.Author;
import com.example.model.Book;
import com.example.model.Genre;

import java.util.List;

public interface BookService {
    Book save(Book book);
    Book findById(Long id);
    List<Book> findAll();
    List<Book> findByAuthor(Author author);
    List<Book> findByTitle(String title);
    List<Book> findByGenre(Genre genre);
    void updateById(Book t, Long id);
    void deleteById(Long id);
    void deleteAll();
    long count();
}
