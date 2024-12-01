package com.project.books.management.exceptions;

public class BookTrackNotFoundException extends RuntimeException {
    public BookTrackNotFoundException(String message) {
        super(message);
    }
}
