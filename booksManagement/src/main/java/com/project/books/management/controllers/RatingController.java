package com.project.books.management.controllers;


import com.project.books.management.components.ClientService;
import com.project.books.management.components.JwtService;
import com.project.books.management.components.RatingService;
import com.project.books.management.dto.RatingDTO;
import com.project.books.management.entities.Client;
import com.project.books.management.entities.Rating;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rating")
public class RatingController {

    final private RatingService ratingService;
    final private JwtService jwtService;
    final private ClientService clientService;

    @PostMapping("rate/{id}")
    public ResponseEntity<RatingDTO> rateBook(@RequestHeader("Authorization") String authHeader,
                                              @PathVariable String id,
                                              @RequestParam double rate){
        String token = authHeader.substring(7);
        Long clientId = jwtService.extractClientIdFromToken(token);
        Client client = clientService.getClientById(clientId, Client.class);
        return new ResponseEntity<>(ratingService.addRating(new Rating(id, rate, client)), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<RatingDTO> updateRating(@RequestBody RatingDTO ratingDTO){
        return new ResponseEntity<>(ratingService.updateRating(ratingDTO), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long id){
        ratingService.deleteRating(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/avg/{id}")
    public ResponseEntity<Double> getAvgRatingForBook(@PathVariable String id){
        return new ResponseEntity<>(ratingService.getAVGRatingByBookId(id), HttpStatus.OK);
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<List<RatingDTO>> getRatingByBookId(@PathVariable String id){
        return new ResponseEntity<>(ratingService.getRatingByBookId(id), HttpStatus.OK);
    }

    @GetMapping("/rating/{bookId}")
    public ResponseEntity<List<RatingDTO>> getRatingGivenToBook(@RequestHeader("Authorization") String authHeader, @PathVariable String bookId){
        String token = authHeader.substring(7);
        Long id = jwtService.extractClientIdFromToken(token);
        return new ResponseEntity<>(ratingService.getRatingByClientIdAndBookId(id, bookId), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<RatingDTO>> getAllRatings(){
        return new ResponseEntity<>(ratingService.getAllRating(), HttpStatus.OK);
    }

}
