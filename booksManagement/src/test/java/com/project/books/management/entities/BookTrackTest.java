package com.project.books.management.entities;

import com.project.books.management.repositories.BookTrackRepository;
import com.project.books.management.repositories.ClientRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookTrackTest {

    private Validator validator;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BookTrackRepository bookTrackRepository;

    Client client;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
        this.client = clientRepository.save(new Client("fm", "f@gmail.com","pass",
                LocalDate.of(2000,1,1), Role.USER));
    }

    @Test
    void shouldReturnDataValidationIssue(){
        BookTrack bookTrack= bookTrackRepository.save(new BookTrack("", client));
        Set<ConstraintViolation<BookTrack>> violations = validator.validate(bookTrack);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("ne doit pas être vide")));
    }

    @Test
    void shouldReturnDataIntegrityViolationException(){
        BookTrack bookTrack = new BookTrack("fdtyihf", client);
        bookTrackRepository.saveAndFlush(bookTrack);
        BookTrack bookTrack2 = new BookTrack("fdtyihf", client);
        assertThrows(DataIntegrityViolationException.class, () -> bookTrackRepository.saveAndFlush(bookTrack2));
    }
}