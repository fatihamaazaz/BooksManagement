package com.project.books.management.exceptions;

public class BookNotTrackedException extends RuntimeException {
    public BookNotTrackedException(String message) {
        super(message);
    }
}
