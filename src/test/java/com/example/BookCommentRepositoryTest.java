package com.example;

import com.example.model.BookComment;
import com.example.repository.BookCommentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
public class BookCommentRepositoryTest {
    private static final String COMMENT = "Excellent book with unpredictable exodus";
    private static final long ID = 1L;

    @Autowired
    private BookCommentRepository bookCommentRepository;


    @Test
    void shouldSaveBookComment() {
        BookComment bookComment = new BookComment(COMMENT);
        int sizeBeforeSaving = bookCommentRepository.findAll().size();
        BookComment savedBookComment = bookCommentRepository.save(bookComment);
        int sizeAfterSaving = bookCommentRepository.findAll().size();
        assertThat(sizeBeforeSaving < sizeAfterSaving);
        assertThat(savedBookComment.getComment()).isEqualTo(COMMENT);
    }

    @Test
    void shouldFindBookCommentById() {
        BookComment bookComment = bookCommentRepository.findById(ID).orElseThrow();
        assertThat(bookComment.getBook().getTitle()).isNotNull();
        assertThat(bookComment.getBook().getAuthor()).isNotNull();
        assertThat(bookComment.getBook().getComments()).isNotNull();
        assertThat(bookComment.getComment()).isNotNull();
    }

    @Test
    void shouldFindAllBookComments() {
        List<BookComment> comments = bookCommentRepository.findAll();
        assertThat(comments).allMatch(comment -> Objects.nonNull(comment.getComment()))
                            .allMatch(comment -> Objects.nonNull(comment.getBook()));
    }

    @Test
    void shouldReturnSpecificBookCommentsSelectedByName(){
        List<BookComment> comments = bookCommentRepository.findByComment("So-so");
        assertThat(comments).allMatch(comment -> comment.getId() != 0)
                .allMatch(comment -> comment.getBook() != null)
                .allMatch(comment -> comment.getComment() != null);
    }


    @Test
    void shouldDeleteBookCommentById() {
        BookComment bookComment = bookCommentRepository.findById(ID).orElseThrow();
        assertThat(bookComment.getComment() != null);
        bookCommentRepository.deleteById(ID);
        assertThatThrownBy(() -> bookCommentRepository.findById(ID).get()).isInstanceOf(NoSuchElementException.class);
    }
}
