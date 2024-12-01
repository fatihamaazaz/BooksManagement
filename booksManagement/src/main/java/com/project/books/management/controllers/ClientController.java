package com.project.books.management.controllers;


import com.project.books.management.components.ClientService;
import com.project.books.management.components.JwtService;
import com.project.books.management.configuration.SecurityConfig;
import com.project.books.management.dto.*;
import com.project.books.management.entities.Client;
import com.project.books.management.exceptions.ClientNotFoundException;
import com.project.books.management.repositories.ClientRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class ClientController {
    private final ClientService clientService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ClientRepository clientRepository;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> AuthenticateAndGetToken(@RequestBody @Valid AuthRequestDTO authRequestDTO){
        return new ResponseEntity<>(clientService.AuthenticateAndGetToken(authRequestDTO), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/clients")
    public ResponseEntity<List<ListClientDTO>> getAllClients(){
        return new ResponseEntity<>(clientService.getAllClient(), HttpStatus.OK);
    }

    @GetMapping("/client")
    public ResponseEntity<ListClientDTO> getClientByToken(@RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        Long id = jwtService.extractClientIdFromToken(token);
        return new ResponseEntity<>(clientService.getClientById(id, ListClientDTO.class), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Client> createClient(@RequestBody @Valid RegisterDTO client){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        client.setPassword(encoder.encode(client.getPassword()));
        return new ResponseEntity<>(clientService.addClient(client), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Client> updateClient(@RequestBody @Valid UpdateClientDTO client, @RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        Long id = jwtService.extractClientIdFromToken(token);
        return new ResponseEntity<>(clientService.updateClient(client, id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id){
        clientService.deleteClient(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/tracks")
    public ResponseEntity<List<BookTrackDTO>> getBooksTrackByClient(@RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        Long clientId = jwtService.extractClientIdFromToken(token);
        return new ResponseEntity<>(clientService.getBookTrackByClientId(clientId), HttpStatus.OK);
    }

    @GetMapping("/trackedBooksIds")
    public ResponseEntity<List<String>> getTrackedBooksIdByClient(@RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        Long clientId = jwtService.extractClientIdFromToken(token);
        return new ResponseEntity<>(clientService.getTrackedBooksIdByClientId(clientId), HttpStatus.OK);
    }

    @GetMapping("/ratings")
    public ResponseEntity<List<RatingDTO>> getGivenRatings(@RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        Long clientId = jwtService.extractClientIdFromToken(token);
        return new ResponseEntity<>(clientService.getRatingByClientId(clientId), HttpStatus.OK);
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<LikedBookDTO>> getFavoriteBooksByClient(@RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        Long clientId = jwtService.extractClientIdFromToken(token);
        return new ResponseEntity<>(clientService.getFavoriteBooksByClientId(clientId), HttpStatus.OK);
    }

    @GetMapping("/likedBooksIds")
    public ResponseEntity<List<String>> getLikedBooksIdByClient(@RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        Long clientId = jwtService.extractClientIdFromToken(token);
        return new ResponseEntity<>(clientService.getLikedBooksIdByClientId(clientId), HttpStatus.OK);
    }

}
