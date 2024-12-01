package com.project.books.management.exceptions;

public class LikedBookNotFoundException extends RuntimeException {
    public LikedBookNotFoundException(String message) {
        super(message);
    }
}
