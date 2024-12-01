package com.project.books.management.entities;

import com.project.books.management.repositories.BookTrackRepository;
import com.project.books.management.repositories.ClientRepository;
import com.project.books.management.repositories.RatingRepository;
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
class RatingTest {

    private Validator validator;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RatingRepository ratingRepository;

    Client client;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
        this.client = clientRepository.save(new Client("fm", "f@gmail.com","pass",
                LocalDate.of(2000,1,1), Role.USER));
    }

    @Test
    void shouldReturnDataValidationIssues(){
        Rating rating = new Rating("", 8, client);
        Set<ConstraintViolation<Rating>> violations = validator.validate(rating);
        System.out.println(violations);
        assertEquals(2, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("doit être inférieur ou égal à 5")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("ne doit pas être vide")));
    }

    @Test
    void shouldReturnDataViolationException(){
        Rating rating = new Rating("fdt", 4, client);
        ratingRepository.saveAndFlush(rating);
        Rating rating2 = new Rating("fdt", 2, client);
        assertThrows(DataIntegrityViolationException.class, () -> ratingRepository.saveAndFlush(rating2));
    }
}