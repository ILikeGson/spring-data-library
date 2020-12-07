package com.example.repository;

import com.example.model.Author;
import com.example.model.Book;
import com.example.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b where b.title = :title")
    List<Book> findByTitle(@Param("title") String title);

    @Query("select b from Book b where b.author = :author")
    List<Book> findByAuthor(@Param("author") Author author);

    @Query("select b from Book b where b.genre = :genre")
    List<Book> findByGenre(@Param("genre") Genre genre);

    @Modifying
    @Query("update Book b set b.title = :title where b.id = :id")
    void updateBookById(@Param("title") String title,  @Param("id") long id);
}
