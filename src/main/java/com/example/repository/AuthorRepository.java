package com.example.repository;

import com.example.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("select a from Author a where a.firstName = :firstName and a.lastName = :lastName ")
    Optional<Author> findByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
