package com.project.books.management.exceptions;

public class RatingNotFoundException extends RuntimeException {
    public RatingNotFoundException(String message) {
        super(message);
    }
}
