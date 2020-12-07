package com.example;

import com.example.model.BookComment;
import com.example.repository.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.example.model.Author;
import com.example.model.Book;
import com.example.model.Genre;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Testing Book DAO... ")
@DataJpaTest
public class BookRepositoryTest {
    private static final String TITLE = "Romeo and Juliet";
    private static final Author AUTHOR = new Author("Alexander", "Djigyrda");
    private static final Genre GENRE = new Genre("comedy");
    private static final List<BookComment> COMMENTS = List.of(new BookComment("I love it"), new BookComment("Best Story ever"));
    private static final long ID = 9L;
    private static final int SIZE = 8;
    private static final long GET_BY_ID = 1L;
    private static final String TITLE_FROM_GOT_BOOK = "Harry Potter and the Sorcerer’s Stone";
    private static final Author AUTHOR_FROM_GOT_BOOK = new Author("Joanne", "Rowling", Set.of());
    private static final Genre GENRE_FROM_GOT_BOOK = new Genre("fantasy");
    private static final long UPDATE_BOOK_BY_ID = 3L;
    private static final String UPDATE_TITLE_BY_ID = "Harry Potter and the Chamber of Demons";

    @Autowired
    private BookRepository bookRepository;


    @DisplayName("Insert and return a new book")
    @Test
    public void shouldInsertNewBookAndReturnId() {
        Book expectedBook = new Book(TITLE, AUTHOR, GENRE, COMMENTS);
        expectedBook.setId(ID);
        Book book = new Book(TITLE, AUTHOR, GENRE, COMMENTS);
        assertThat(bookRepository.save(book)).isEqualToComparingOnlyGivenFields(expectedBook, "title", "author", "id", "genre");
    }


    @DisplayName("Get all book from DB")
    @Test
    public void shouldReturnAllBooksFromDB() {
        List<Book> books = bookRepository.findAll();
        assertThat(books).allMatch(x -> x.getTitle() != null && x.getTitle().length() > 0)
                .allMatch(x -> x.getGenre().getGenreName() != null && x.getGenre().getGenreName().length() > 0)
                .allMatch(x -> x.getAuthor().getFirstName() != null && x.getAuthor().getFirstName().length() > 0)
                .allMatch(x -> x.getAuthor().getLastName() != null && x.getAuthor().getLastName().length() > 0)
                .allMatch(x -> x.getAuthor().getBooks() != null)
                .allMatch(x -> x.getId() > 0);
        assertThat(books.size()).isEqualTo(SIZE);
    }

    @DisplayName("Get specifying book by id")
    @Test
    public void shouldReturnBookByGivenId() {
        AUTHOR_FROM_GOT_BOOK.setId(1L);
        Book book = bookRepository.findById(GET_BY_ID).orElseThrow(IllegalArgumentException::new);
        assertThat(book.getTitle()).isEqualTo(TITLE_FROM_GOT_BOOK);
        assertThat(book.getAuthor().getFirstName()).isEqualTo(AUTHOR_FROM_GOT_BOOK.getFirstName());
        assertThat(book.getAuthor().getLastName()).isEqualTo(AUTHOR_FROM_GOT_BOOK.getLastName());
        assertThat(book.getAuthor().getId()).isEqualTo(AUTHOR_FROM_GOT_BOOK.getId());
        assertThat(book.getGenre().getGenreName()).isEqualTo(GENRE_FROM_GOT_BOOK.getGenreName());
    }

    @DisplayName("Update a book")
    @Test
    public void shouldUpdateBookByGivenId() {
        Book book = new Book(UPDATE_TITLE_BY_ID, AUTHOR_FROM_GOT_BOOK, GENRE_FROM_GOT_BOOK, List.of());
        BookComment comment = new BookComment("fdfdfdfdf", book);
        comment.setId(11L);
        book.setComments(List.of(comment));
        bookRepository.updateBookById(book.getTitle(), UPDATE_BOOK_BY_ID);

        Book updatedBook = bookRepository.findById(UPDATE_BOOK_BY_ID).orElseThrow(IllegalArgumentException::new);
        assertThat(updatedBook).hasFieldOrPropertyWithValue("title", UPDATE_TITLE_BY_ID);
        assertThat(updatedBook).hasFieldOrPropertyWithValue("genre", GENRE_FROM_GOT_BOOK);
        assertThat(updatedBook.getComments().get(0)).isEqualTo(comment);
    }

    @DisplayName("Delete a book")
    @Test
    public void shouldDeleteBookByGivenId() {
        Book book = bookRepository.findById(UPDATE_BOOK_BY_ID).orElseThrow();
        assertThat(book).isNotNull();
        bookRepository.deleteById(UPDATE_BOOK_BY_ID);
        assertThatThrownBy(() -> bookRepository.findById(UPDATE_BOOK_BY_ID).get()).isInstanceOf(NoSuchElementException.class);
    }
}
