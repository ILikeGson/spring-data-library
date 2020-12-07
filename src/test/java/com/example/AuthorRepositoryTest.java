package com.example;

import com.example.model.Author;
import com.example.repository.AuthorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Author repo test")
@DataJpaTest
public class AuthorRepositoryTest {

    private static final String FIRST_NAME = "Maria";
    private static final String LAST_NAME = "Bao";
    private static final long ID = 1L;

    @Autowired
    private AuthorRepository authorRepository;


    @Test
    void shouldSaveAuthorAndReturnIt() {
        Author author = new Author("Vasya", "Ivanov");
        Author savedAuthor = authorRepository.save(author);
        assertThat(savedAuthor.getLastName()).isEqualTo(author.getLastName());
        assertThat(savedAuthor.getFirstName()).isEqualTo(author.getFirstName());
        assertThat(savedAuthor.getId()).isNotNull();
    }

    @Test
    void shouldFindAuthorById(){
        Author foundAuthor = authorRepository.findById(1L).orElseThrow(IllegalArgumentException::new);
        assertThat(foundAuthor.getLastName()).isEqualTo("Rowling");
        assertThat(foundAuthor.getFirstName()).isEqualTo("Joanne");
        assertThat(foundAuthor.getId()).isNotNull();
    }

    @Test
    void shouldReturnListOfAllAuthors() {
        assertThat(authorRepository.findAll().size() > 0);
    }

    @Test
    void shouldFindAuthorByName(){
        Optional<Author> authors = authorRepository.findByFirstNameAndLastName("George", "Martin");
        assertThat(authors.get()).matches(author -> author.getLastName() != null)
                .matches(author -> author.getFirstName() != null)
                .matches(author -> !author.getBooks().isEmpty())
                .matches(author -> author.getId() != 0);
    }

    @Test
    void shouldUpdateAuthorInfo() {
        Author author = authorRepository.findById(ID).orElseThrow();
        author.setFirstName(FIRST_NAME);
        author.setLastName(LAST_NAME);
        author = authorRepository.findById(ID).orElseThrow();
        assertThat(author.getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(author.getLastName()).isEqualTo(LAST_NAME);
    }

    @Test
    void shouldDeleteAuthorByGivenId() {
        Author author = authorRepository.findById(ID).orElseThrow();
        assertThat(author).isNotNull();
        authorRepository.deleteById(ID);
        assertThatThrownBy(() -> authorRepository.findById(ID).get()).isInstanceOf(NoSuchElementException.class);
    }

}
