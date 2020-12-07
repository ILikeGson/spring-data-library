package com.example.service;

import com.example.model.BookComment;

import java.util.List;

public interface BookCommentService {
    BookComment save(BookComment comment);
    BookComment findById(Long id);
    List<BookComment> findAll();
    List<BookComment> findByComment(String comment);
    void updateById(BookComment comment, Long id, Long bookId);
    void deleteById(Long id);
}
