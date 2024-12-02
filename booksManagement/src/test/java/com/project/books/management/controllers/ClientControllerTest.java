package com.project.books.management.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.books.management.components.ClientService;
import com.project.books.management.components.JwtAuthService;
import com.project.books.management.dto.AuthRequestDTO;
import com.project.books.management.dto.RegisterDTO;
import com.project.books.management.entities.Client;
import com.project.books.management.repositories.ClientRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private JwtAuthService jwtAuthService;

    @Autowired
    private MockMvc mockMvc;

    Client client;
    String token;

    @BeforeEach
    void setUp(){
        this.client = clientService.addClient(new RegisterDTO("fati", "fati@gmail.com",
                "pass", LocalDate.of(2000,1,1)));
        this.token = "Bearer " + jwtAuthService.AuthenticateAndGetToken(new AuthRequestDTO(client.getUserName(), "pass")).getAccessToken();
    }

    @AfterEach
    void tearDown() {
        clientRepository.delete(client);
    }

    @Test
    void shouldAuthenticateSuccessfuly() throws Exception {
        AuthRequestDTO credentials = new AuthRequestDTO(client.getUserName(), "pass");
        this.mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(credentials)))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturnDataValidationError() throws Exception {
        AuthRequestDTO credentials = new AuthRequestDTO("", "pass");
        this.mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(credentials)))
                .andExpect(status().isBadRequest()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldSuccessfulyReturnUserData() throws Exception{
        this.mockMvc.perform(get("/client").header("Authorization", this.token))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}