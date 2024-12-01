package com.project.books.management.controllers;

import com.project.books.management.dto.ErrorDTO;
import com.project.books.management.exceptions.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalAdviceController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ErrorDTO error = new ErrorDTO("Arguments validation error",
                ex.getBindingResult().getFieldErrors().getFirst().getField() + " " +
                        ex.getBindingResult().getFieldErrors().getFirst().getDefaultMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDTO> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String details = ex.getMostSpecificCause().getMessage().contains("email") ? "email" : "username";
        ErrorDTO error = new ErrorDTO("Data integrity error", "Dupliacted " + details );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleClientNotFoundException(ClientNotFoundException ex){
        ErrorDTO error = new ErrorDTO("Client not found exception", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LikedBookNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleLikedBookNotFoundException(LikedBookNotFoundException ex){
        ErrorDTO error = new ErrorDTO("Liked Book not found exception", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RatingNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleRatingNotFoundException(RatingNotFoundException ex){
        ErrorDTO error = new ErrorDTO("Rating not found exception", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDTO> handleBadCredentialsException(BadCredentialsException ex){
        ErrorDTO error = new ErrorDTO("Credentials exception", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LikedBookNotAccesibleException.class)
    public ResponseEntity<ErrorDTO> handleLikedBookNotAccesibleException(LikedBookNotAccesibleException ex){
        ErrorDTO error = new ErrorDTO("Liked Book not accessible exception", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BookNotTrackedException.class)
    public ResponseEntity<ErrorDTO> handleBookNotTrackedException(BookNotTrackedException ex){
        ErrorDTO error = new ErrorDTO("Book not tracked exception", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookNotLikedException.class)
    public ResponseEntity<ErrorDTO> handleBookNotLikedException(BookNotLikedException ex){
        ErrorDTO error = new ErrorDTO("Book not liked exception", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookNotRatedException.class)
    public ResponseEntity<ErrorDTO> handleBookNotRatedException(BookNotRatedException ex){
        ErrorDTO error = new ErrorDTO("Book has not been rated exception", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
