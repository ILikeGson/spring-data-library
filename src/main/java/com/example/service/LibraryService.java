package com.example.service;

public interface LibraryService {
    String saveBook(String title, String authorName, String authorLastName, String genre);
    String saveAuthor(String firstName, String lastName);
    String saveGenre(String genre);
    String saveCommentToBook(String comment, long id);
    String findBookById(long id);
    String findBooksByTitle(String title);
    String findBooksByAuthor(String authorName, String authorLastName);
    String findBooksByGenre(String genre);
    String findAllBooks();
    String findAllGenres();
    String findAllAuthors();
    long countAllBooks();
    void updateBookById(String title, String authorFirstName, String authorLastName, String genre, long id);
    void deleteBookById(long id);
    void deleteAll();
}
