package com.project.books.management.entities;

import com.project.books.management.repositories.ClientRepository;
import com.project.books.management.repositories.LikedBookRepository;
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
class LikedBookTest {

    private Validator validator;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private LikedBookRepository likedBookRepository;

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
        LikedBook likedBook= likedBookRepository.save(new LikedBook("", client));
        Set<ConstraintViolation<LikedBook>> violations = validator.validate(likedBook);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("ne doit pas être vide")));
    }

    @Test
    void shouldReturnDataIntegrityViolationException(){
        LikedBook likedBook = new LikedBook("fdtyihf", client);
        likedBookRepository.saveAndFlush(likedBook);
        LikedBook likedBook2 = new LikedBook("fdtyihf", client);
        assertThrows(DataIntegrityViolationException.class, () -> likedBookRepository.saveAndFlush(likedBook2));
    }

}