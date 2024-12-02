package com.project.books.management.components;

import com.project.books.management.dto.*;
import com.project.books.management.entities.Client;
import com.project.books.management.entities.Role;
import com.project.books.management.exceptions.ClientNotFoundException;
import com.project.books.management.mapping.ClientMapping;
import com.project.books.management.repositories.ClientRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapping clientMapping;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    String msgOfClientNotFoundException = "Client of Id %d not found";

    public <T> T getClientById(Long id, Class<T> targetClass){
        Client client = clientRepository.findClientById(id).orElseThrow(() ->
                new ClientNotFoundException(String.format(msgOfClientNotFoundException, id)));
        return clientMapping.mapFromClient(client, targetClass);
    }

    public List<ListClientDTO> getAllClient(){
        return clientRepository.findAll().stream().map(c -> clientMapping.mapFromClient(c, ListClientDTO.class)).collect(Collectors.toList());
    }

    public Client addClient(RegisterDTO newClient){
        Client client = clientMapping.mapToClient(newClient);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        client.setPassword(encoder.encode(newClient.getPassword()));
        client.setRole(Role.USER);
        return clientRepository.save(client);
    }

    public Client updateClient(UpdateClientDTO client, Long id){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Client oldClient = clientRepository.findClientById(id).orElseThrow(()-> new ClientNotFoundException(String.format(msgOfClientNotFoundException, id)));
        if(!client.getPassword().isBlank()){
            oldClient.setPassword(encoder.encode(client.getPassword()));
        }
        oldClient.setUserName(client.getUserName());
        oldClient.setEmail(client.getEmail());
        oldClient.setBirthDate(client.getBirthDate());

        return clientRepository.save(oldClient);
    }

    public void deleteClient(Long id){
        if(!clientRepository.existsById(id)){
            throw new ClientNotFoundException(String.format(msgOfClientNotFoundException, id));
        }
        clientRepository.deleteClientById(id);
    }

    public List<LikedBookDTO> getFavoriteBooksByClientId(Long id){
        if(!clientRepository.existsById(id)){
            throw new ClientNotFoundException(String.format(msgOfClientNotFoundException, id));
        }
        return clientRepository.findBookByClientId(id);
    }

    public List<BookTrackDTO> getBookTrackByClientId(Long id){
        return clientRepository.findBookTrackByClientId(id);
    }

    public List<RatingDTO> getRatingByClientId(Long id){
        return clientRepository.findRatingByClientId(id);
    }

    public List<String> getTrackedBooksIdByClientId(Long clientId) {
        return clientRepository.findTrackedBooksIdByClientId(clientId);
    }

    public List<String> getLikedBooksIdByClientId(Long clientId) {
        return clientRepository.findLikedBooksIdByClientId(clientId);
    }
}
