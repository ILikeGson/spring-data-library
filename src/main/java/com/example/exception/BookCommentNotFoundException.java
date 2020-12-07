package com.example.exception;

public class BookCommentNotFoundException extends RuntimeException{
    public BookCommentNotFoundException(String message) {
        super(message);
    }
}
