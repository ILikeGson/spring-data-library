package com.example.shell;

import com.example.service.*;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class LibraryShellCommands {
    private static final String BOOK_QUANTITY = "Library consists %d books\n";
    private final IOService consoleIOService;
    private final LibraryService libraryService;

    public LibraryShellCommands(IOService consoleIOService, LibraryService libraryService) {
        this.consoleIOService = consoleIOService;
        this.libraryService = libraryService;
    }


    @ShellMethod(value = "Insert command", key = "saveBook")
    public void saveBook(String title, String authorFirstName, String authorLastName, String genre) {
        consoleIOService.out(libraryService.saveBook(title, authorFirstName, authorLastName, genre));
    }

    @ShellMethod(value = "save author")
    public void saveAuthor(String firstName, String lastName) {
        consoleIOService.out(libraryService.saveAuthor(firstName, lastName));
    }

    @ShellMethod(value = "save genre")
    public void saveGenre(String genre) {
        consoleIOService.out(libraryService.saveGenre(genre));
    }

    @ShellMethod(value = "save comment to book")
    private void saveCommentToBook(String comment, long id) {
        consoleIOService.out(libraryService.saveCommentToBook(comment, id));
    }

    @ShellMethod(value = "Get book by id", key = "findBookById")
    public void findBookById(long id) {
        consoleIOService.out(libraryService.findBookById(id));
    }

    @ShellMethod(value = "Get books by title")
    public void findBooksByTitle(String title) {
        consoleIOService.out(libraryService.findBooksByTitle(title));
    }

    @ShellMethod(value = "Get books by author")
    public void findBooksByAuthor(String authorFirstName, String authorLastName) {
        consoleIOService.out(libraryService.findBooksByAuthor(authorFirstName, authorLastName));
    }

    @ShellMethod(value = "Get books by genre")
    public void findBooksByGenre(String genre) {
        consoleIOService.out(libraryService.findBooksByGenre(genre));
    }

    @ShellMethod(value = "Get all books", key = "findAllBooks")
    public void findAllBooks() {
        consoleIOService.out(libraryService.findAllBooks());
    }

    @ShellMethod(value = "get all genres from DB")
    public void findAllGenres() {
        consoleIOService.out(libraryService.findAllGenres());
    }

    @ShellMethod(value = "get all authors from DB")
    public void findAllAuthors() {
        consoleIOService.out(libraryService.findAllAuthors());
    }

    @ShellMethod(value = "Update by id command")
    public void updateBookById(String title, String authorFirstName, String authorLastName, String genre, long id) {
        libraryService.updateBookById(title, authorFirstName, authorLastName, genre, id);
    }

    @ShellMethod(value = "delete by id command")
    public void deleteBookById(long id) {
        libraryService.deleteBookById(id);
    }

    @ShellMethod(value = "count all books")
    public void countAllBooks() {
        consoleIOService.out(String.format(BOOK_QUANTITY, libraryService.countAllBooks()));
    }

    @ShellMethod(value = "detele all books from library")
    public void deleteAllBooks() {
        libraryService.deleteAll();
    }
}
