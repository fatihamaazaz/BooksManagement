package com.project.books.management.components;

import com.project.books.management.dto.ListClientDTO;
import com.project.books.management.dto.RegisterDTO;
import com.project.books.management.dto.UpdateClientDTO;
import com.project.books.management.entities.Client;
import com.project.books.management.entities.Role;
import com.project.books.management.exceptions.ClientNotFoundException;
import com.project.books.management.repositories.ClientRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClientServiceTest{

    @Autowired
    private ClientService underTestService;

    @Autowired
    private ClientRepository clientRepository;

    Client client;

    @BeforeEach
    void setUp(){
        client = clientRepository.saveAndFlush(new Client("fm", "f@gmail.com","pass",
                LocalDate.of(2000,1,1), Role.USER));
    }

    @AfterEach
    void tearDown() {
        clientRepository.deleteAll();
    }

    @Test
    void shouldReturnClientDTObyClientId(){
        ListClientDTO clientDTO = underTestService.getClientById(client.getId(), ListClientDTO.class);
        ListClientDTO expectedResult = new ListClientDTO(client.getUserName(), client.getEmail(), client.getPassword(),
                client.getBirthDate(), client.getRole());
        assertNotNull(clientDTO);
        assertEquals(expectedResult, clientDTO);
    }

    @Test
    void shouldReturnClientNotFoundForInvalidId(){
        assertThrows(ClientNotFoundException.class, () -> underTestService.getClientById(999L, ListClientDTO.class));
    }

    @Test
    void shouldReturnAllClientsInDatabase(){
        List<ListClientDTO> returnedResult = underTestService.getAllClient();
        List<ListClientDTO> expectedResult = List.of(new ListClientDTO(client.getUserName(), client.getEmail(), client.getPassword(),
                client.getBirthDate(), client.getRole()));

        assertFalse(returnedResult.isEmpty());
        assertEquals(expectedResult, returnedResult);
    }

    @Test
    void shouldRegisterNewClient(){
        List<Client> existingClients = clientRepository.findAll();
        assertEquals(1, existingClients.size());
        Client newClient = underTestService.addClient(new RegisterDTO("fati", "fati@gmail.com", "pass", LocalDate.of(2000,1,1)));
        assertNotNull(newClient);
        List<Client> existingClientAfterRgistry = clientRepository.findAll();
        assertEquals(2, existingClientAfterRgistry.size());
    }

    @Test
    void shouldUpdateExistingClient(){
        String newUsername = "chouchou";
        UpdateClientDTO updates = new UpdateClientDTO(newUsername, client.getEmail(), client.getPassword(), client.getBirthDate());
        Client updatedClient = underTestService.updateClient(updates, client.getId());
        assertNotNull(updatedClient);
        assertEquals(newUsername, updatedClient.getUserName());
        Client clientCurrent = clientRepository.findClientById(client.getId()).get();
        assertEquals(newUsername, clientCurrent.getUserName());
    }

    @Test
    void shouldDeleteExistingClient(){
        List<Client> existingClients = clientRepository.findAll();
        assertEquals(1, existingClients.size());
        underTestService.deleteClient(client.getId());
        List<Client> existingClientAfterDeletion = clientRepository.findAll();
        assertEquals(0, existingClientAfterDeletion.size());
    }

    @Test
    void shouldReturnClientNotFoundAfterDeletingUnexistingClient(){
        assertThrows(ClientNotFoundException.class, () -> underTestService.deleteClient(999L));
    }

}