package com.example.service;

import com.example.exception.BookNotFoundException;
import com.example.model.Author;
import com.example.model.Book;
import com.example.model.Genre;
import com.example.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private static final String EXCEPTION_MESSAGE = "Book with id %d not found";
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Transactional(readOnly = true)
    @Override
    public Book findById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()){
            return book.get();
        }
        throw new BookNotFoundException(String.format(EXCEPTION_MESSAGE, id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> findByAuthor(Author author) {
        return bookRepository.findByAuthor(author);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> findByGenre(Genre genre) {
        return bookRepository.findByGenre(genre);
    }

    @Transactional
    public void updateById(Book book, Long id) {
        bookRepository.updateBookById(book.getTitle(), id);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAll() {
        bookRepository.deleteAll();
    }

    @Transactional(readOnly = true)
    @Override
    public long count() {
        return bookRepository.count();
    }

}
