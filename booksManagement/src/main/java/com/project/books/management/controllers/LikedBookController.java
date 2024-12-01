package com.project.books.management.controllers;

import com.project.books.management.components.ClientService;
import com.project.books.management.components.JwtService;
import com.project.books.management.components.LikedBookService;
import com.project.books.management.dto.LikedBookDTO;
import com.project.books.management.entities.Client;
import com.project.books.management.entities.LikedBook;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favoritebook")
@RequiredArgsConstructor
public class LikedBookController {
    final private LikedBookService likedBookService;
    final private JwtService jwtService;
    final private ClientService clientService;

    @PostMapping("/like/{id}")
    public ResponseEntity<LikedBookDTO> likeBook(@RequestHeader("Authorization") String authHeader, @PathVariable String id){
        String token = authHeader.substring(7);
        Long clientId = jwtService.extractClientIdFromToken(token);
        Client client = clientService.getClientById(clientId, Client.class);
        return new ResponseEntity<>(likedBookService.addLikedBook(new LikedBook(id, client)), HttpStatus.CREATED);
    }

    @DeleteMapping("/unlike/{id}")
    public ResponseEntity<Void> unlikeBook(@RequestHeader("Authorization") String authHeader, @PathVariable Long id){
        String token = authHeader.substring(7);
        Long clientId = jwtService.extractClientIdFromToken(token);
        Client client = clientService.getClientById(clientId, Client.class);
        likedBookService.deleteLikedBook(id, client);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteLikedBookByBookId(@RequestHeader("Authorization") String authHeader, @PathVariable String id){
        String token = authHeader.substring(7);
        Long clientId = jwtService.extractClientIdFromToken(token);
        likedBookService.deleteLikedBookByBookId(clientId, id);
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/likedbooks/all")
    public ResponseEntity<List<LikedBookDTO>> getAllLikedBooks(){
        return new ResponseEntity<>(likedBookService.getAllLikedBooks(), HttpStatus.OK);
    }

}
