package com.project.books.management.entities;

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
class ClientTest {

    private Validator validator;

    @Autowired
    private ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void shouldDetectViolationWhenClientIsInvalid(){
        Client client = new Client("", "fm", "", LocalDate.of(2000,01,01), Role.USER);
        Set<ConstraintViolation<Client>> violations = validator.validate(client);
        assertEquals(3, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("ne doit pas être vide")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("doit être une adresse électronique syntaxiquement correcte")));
    }

    @Test
    void shouldNotSaveClientWhenDataIsInvalid(){
        Client client = new Client("fm", "f@gmail.com","pass",
                LocalDate.of(2000,1,1), Role.USER);
        Client clientDuplicateUsername = new Client("fm", "fm@gmail.com","password",
                LocalDate.of(2000,2,25), Role.USER);

        clientRepository.save(client);
        assertThrows(DataIntegrityViolationException.class,() -> clientRepository.saveAndFlush(clientDuplicateUsername));
    }



}