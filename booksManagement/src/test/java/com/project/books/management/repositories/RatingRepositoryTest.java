package com.project.books.management.repositories;

import com.project.books.management.dto.RatingDTO;
import com.project.books.management.entities.BookTrack;
import com.project.books.management.entities.Client;
import com.project.books.management.entities.Rating;
import com.project.books.management.entities.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class RatingRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RatingRepository underTestRepository;

    Client client;
    Rating rating;

    String unratedBookId = "fffff";

    @BeforeEach
    void setUp(){
        this.client = clientRepository.save(new Client("fm", "f@gmail.com","pass",
                LocalDate.of(2000,1,1), Role.USER));
        this.rating = underTestRepository.save(new Rating("gtrffs", 4,client));
    }

    @AfterEach
    void tearDown() {
        clientRepository.delete(client);
        underTestRepository.delete(rating);
    }

    @Test
    void shouldfindAVGRatingByRatedBookId(){
        Optional<Double> avg = underTestRepository.findAVGRatingByBookId(rating.getBookId());
        assertFalse(avg.isEmpty());
        assertEquals(rating.getRate(), avg.get());
    }

    @Test
    void shouldfindAVGRatingByUnratedBookId(){
        Optional<Double> avg = underTestRepository.findAVGRatingByBookId(unratedBookId);
        assertNotNull(avg);
        assertTrue(avg.isEmpty());
    }

    @Test
    void shouldfindRatingsByRatedBookId(){
        List<RatingDTO> ratings = underTestRepository.findRatingsByBookId(rating.getBookId());
        List<RatingDTO> expectedResult = List.of(new RatingDTO(rating.getId(), rating.getRate(), rating.getBookId()));
        assertFalse(ratings.isEmpty());
        assertEquals(expectedResult, ratings);
    }

    @Test
    void shouldfindRatingsByUnratedBookId(){
        List<RatingDTO> ratings = underTestRepository.findRatingsByBookId(unratedBookId);
        assertNotNull(ratings);
        assertTrue(ratings.isEmpty());
    }

    @Test
    void shouldfindRatingByRatedBookAndClientId(){
        List<Rating> ratings = underTestRepository.findRatingByBookAndClientId(client.getId(), rating.getBookId());
        List<Rating> expectedResult = List.of(rating);
        assertNotNull(ratings);
        assertEquals(expectedResult, ratings);
    }

    @Test
    void shouldfindRatingByUnratedBookAndClientId(){
        List<Rating> ratings = underTestRepository.findRatingByBookAndClientId(client.getId(), unratedBookId);
        assertNotNull(ratings);
        assertTrue(ratings.isEmpty());
    }
}