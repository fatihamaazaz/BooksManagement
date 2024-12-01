package com.project.books.management.exceptions;

public class BookNotRatedException extends RuntimeException {
    public BookNotRatedException(String message) {
        super(message);
    }
}
