package com.project.books.management.controllers;


import com.project.books.management.components.BookTrackService;
import com.project.books.management.components.ClientService;
import com.project.books.management.components.JwtService;
import com.project.books.management.dto.BookTrackDTO;
import com.project.books.management.entities.BookTrack;
import com.project.books.management.entities.Client;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booktrack")
@RequiredArgsConstructor
public class BookTrackController {

    final private BookTrackService bookTrackService;
    final private JwtService jwtService;
    final private ClientService clientService;

    @PostMapping("/add/{id}")
    public ResponseEntity<BookTrackDTO> addBookToTrack(@RequestHeader("Authorization") String authHeader, @PathVariable String id){
        String token = authHeader.substring(7);
        Long clientId = jwtService.extractClientIdFromToken(token);
        Client client = clientService.getClientById(clientId, Client.class);
        return new ResponseEntity<>(
                bookTrackService.addBookTrack(new BookTrack(id, client)),
                HttpStatus.OK
        );
    }

    @PutMapping("/update")
    public ResponseEntity<BookTrackDTO> updateBookTrack(@RequestBody @Valid BookTrackDTO bookTrackDTO){
        return new ResponseEntity<>(bookTrackService.updateBookTrack(bookTrackDTO), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBookTrack(@RequestHeader("Authorization") String authHeader, @PathVariable Long id){
        String token = authHeader.substring(7);
        Long clientId = jwtService.extractClientIdFromToken(token);
        Client client = clientService.getClientById(clientId, Client.class);
        bookTrackService.deleteBookTrack(id, client);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<BookTrackDTO>> getAllBookTrack(){
        return new ResponseEntity<>(bookTrackService.getAllBookTrack(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookTrackByBookId(@RequestHeader("Authorization") String authHeader, @PathVariable String id){
        String token = authHeader.substring(7);
        Long clientId = jwtService.extractClientIdFromToken(token);
        bookTrackService.deleteTrackByBookId(clientId, id);
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }

}
