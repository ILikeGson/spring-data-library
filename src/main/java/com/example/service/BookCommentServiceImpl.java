package com.example.service;

import com.example.exception.BookCommentNotFoundException;
import com.example.model.Book;
import com.example.model.BookComment;
import com.example.repository.BookCommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookCommentServiceImpl implements BookCommentService{
    private static final String EXCEPTION_MESSAGE = "Book comment with id %d not found";
    private final BookCommentRepository bookCommentRepository;

    public BookCommentServiceImpl(BookCommentRepository bookCommentRepository) {
        this.bookCommentRepository = bookCommentRepository;
    }

    @Transactional
    @Override
    public BookComment save(BookComment comment) {
        return bookCommentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public BookComment findById(Long id) {
        Optional<BookComment> comment = bookCommentRepository.findById(id);
        if (comment.isPresent()) {
            return comment.get();
        }
        throw new BookCommentNotFoundException(String.format(EXCEPTION_MESSAGE, id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookComment> findAll() {
        return bookCommentRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookComment> findByComment(String comment) {
        return bookCommentRepository.findByComment(comment);
    }

    @Transactional
    @Override
    public void updateById(BookComment comment, Long commentId, Long bookId) {
        bookCommentRepository.deleteById(commentId);
        Book book = new Book();
        book.setId(bookId);
        comment.setBook(book);
        comment.setId(commentId);
        save(comment);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        bookCommentRepository.deleteById(id);
    }
}
