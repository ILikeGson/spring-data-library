package com.example.service;

import com.example.model.Author;
import com.example.model.Book;
import com.example.model.BookComment;
import com.example.model.Genre;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class LibraryServiceImpl implements LibraryService{
    private static final String SAVED_BOOK_INFO = "Book with title (%s) were saved with id (%d)\n";
    private static final String SAVED_BOOK_COMMENT_INFO = "Comment (%s) were saved with id (%d) for book with title (%s)\n";
    private static final String FOUND_BOOK = "id: %d\ntitle: %s\nauthor: %s\ngenre: %s\ncomments: %s\n";
    private static final String SAVED_AUTHOR = "id: %d\nname: %s %s\n";
    private static final String SAVED_GENRE = "id: %d\ngenre: %s\n";

    private final BookService bookService;
    private final AuthorService authorService;
    private final BookCommentService commentService;
    private final GenreService genreService;

    public LibraryServiceImpl(BookService bookService, AuthorService authorService,
                              BookCommentService commentService, GenreService genreService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.commentService = commentService;
        this.genreService = genreService;
    }

    @Transactional
    @Override
    public String saveBook(String title, String authorFirstName, String authorLastName, String genre) {
        Book book = new Book();
        Author author = new Author();
        Genre bookGenre = new Genre();
        book.setTitle(title);
        bookGenre.setGenreName(genre);
        author.setFirstName(authorFirstName);
        author.setLastName(authorLastName);
        Optional<Genre> foundGenre = genreService.findByGenre(genre);
        if (foundGenre.isPresent()) {
            book.setGenre(foundGenre.get());
        } else {
            book.setGenre(genreService.save(bookGenre));
        }
        Optional<Author> foundAuthor = authorService.findByFirstNameAndLastName(authorFirstName, authorLastName);
        if (foundAuthor.isPresent()) {
            book.setAuthor(foundAuthor.get());
        } else {
            book.setAuthor(authorService.save(author));
        }
        Book savedBook = bookService.save(book);
        return String.format(SAVED_BOOK_INFO, savedBook.getTitle(), savedBook.getId());
    }

    @Transactional
    @Override
    public String saveAuthor(String firstName, String lastName) {
        Author author = authorService.save(new Author(firstName, lastName));
        return String.format(SAVED_AUTHOR, author.getId(), author.getFirstName(), author.getLastName());
    }

    @Transactional
    @Override
    public String saveGenre(String genre) {
        Genre savedGenre = genreService.save(new Genre(genre));
        return String.format(SAVED_GENRE, savedGenre.getId(), savedGenre.getGenreName());
    }

    @Transactional
    @Override
    public String saveCommentToBook(String comment, long id) {
        Book book = bookService.findById(id);
        BookComment bookComment = new BookComment(comment);
        bookComment.setBook(book);
        BookComment savedBookComment = commentService.save(bookComment);
        return String.format(SAVED_BOOK_COMMENT_INFO, savedBookComment.getComment(),
                savedBookComment.getId(), savedBookComment.getBook().getTitle());
    }

    @Transactional(readOnly = true)
    @Override
    public String findBookById(long id) {
        Book book = bookService.findById(id);
        return String.format(FOUND_BOOK, book.getId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getComments());
    }

    @Transactional(readOnly = true)
    @Override
    public String findBooksByTitle(String title) {
        StringBuilder builder = new StringBuilder();
        bookService.findByTitle(title).forEach(book -> builder.append(
                String.format(FOUND_BOOK, book.getId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getComments())));
        return builder.toString();
    }

    @Transactional(readOnly = true)
    @Override
    public String findBooksByAuthor(String authorName, String authorLastName) {
        StringBuilder builder = new StringBuilder();
        Author author = authorService.findByFirstNameAndLastName(authorName, authorLastName).orElseThrow();
        author.getBooks().forEach(book -> builder.append(
                String.format(FOUND_BOOK, book.getId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getComments())));

        return builder.toString();
    }

    @Transactional(readOnly = true)
    @Override
    public String findBooksByGenre(String genre) {
        StringBuilder builder = new StringBuilder();
        genreService.findByGenre(genre).orElseThrow().getBooks().forEach(book -> builder.append(
                String.format(FOUND_BOOK, book.getId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getComments())));
        return builder.toString();
    }

    @Transactional(readOnly = true)
    @Override
    public String findAllBooks() {
        StringBuilder builder = new StringBuilder();
        bookService.findAll().forEach(book -> builder.append(
                String.format(FOUND_BOOK, book.getId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getComments())));
        return builder.toString();
    }

    @Transactional(readOnly = true)
    @Override
    public String findAllGenres() {
        StringBuilder builder = new StringBuilder();
        genreService.findAll().forEach(genre -> builder.append(String.format(SAVED_GENRE, genre.getId(), genre.getGenreName())));
        return builder.toString();
    }

    @Transactional(readOnly = true)
    @Override
    public String findAllAuthors() {
        StringBuilder builder = new StringBuilder();
        authorService.findAll().forEach(author -> builder.append(String.format(SAVED_AUTHOR, author.getId(), author.getFirstName(), author.getLastName())));
        return builder.toString();
    }

    @Transactional(readOnly = true)
    @Override
    public long countAllBooks() {
        return bookService.count();
    }

    @Transactional
    @Override
    public void updateBookById(String title, String authorFirstName, String authorLastName, String genre, long id) {
        Optional<Author> authorOptional = authorService.findByFirstNameAndLastName(authorFirstName, authorLastName);
        Optional<Genre> genreOptional = genreService.findByGenre(genre);
        Book book = bookService.findById(id);
        book.setTitle(title);
        if (!book.getAuthor().getFirstName().equals(authorFirstName) || !book.getAuthor().getLastName().equals(authorLastName)) {
            book.getAuthor().setFirstName(authorFirstName);
            book.getAuthor().setLastName(authorLastName);
        }
        if (authorOptional.isEmpty()) {
            book.setAuthor(authorService.save(new Author(authorFirstName, authorLastName)));
        } else {
            book.setAuthor(authorOptional.get());
        }

        if (genreOptional.isEmpty()) {
            book.setGenre(genreService.save(new Genre(genre)));
        } else {
            book.setGenre(genreOptional.get());
        }
        bookService.updateById(book, id);
    }

    @Transactional
    @Override
    public void deleteBookById(long id) {
        bookService.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAll() {
        bookService.deleteAll();
    }
}
