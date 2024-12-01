package com.project.books.management.repositories;

import com.project.books.management.dto.BookTrackDTO;
import com.project.books.management.dto.LikedBookDTO;
import com.project.books.management.dto.RatingDTO;
import com.project.books.management.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class ClientRepositoryTest {

    @Autowired
    private ClientRepository underTestRepository;

    @Autowired
    private LikedBookRepository likedBookRepository;

    @Autowired
    private BookTrackRepository bookTrackRepository;

    @Autowired
    private RatingRepository ratingRepository;

    Client client;
    LikedBook likedBook;
    BookTrack track;
    Rating rating;
    Long inexistantClientId = 999L;

    @BeforeEach
    void setUp(){
        this.client = underTestRepository.save(new Client("fm", "f@gmail.com","pass",
                LocalDate.of(2000,1,1), Role.USER));
        this.likedBook= likedBookRepository.save(new LikedBook("gtrffs", client));
        this.track = bookTrackRepository.save(new BookTrack("gtrffs", client));
        this.rating = ratingRepository.save(new Rating("gtrffs", 4, this.client));
    }

    @Test
    void shouldfindLikedBookByExistantClientId() {
        List<LikedBookDTO> expectedResult = List.of(new LikedBookDTO(this.likedBook.getId(), this.likedBook.getBookId()));
        List<LikedBookDTO> returnedLikedBookDTO = underTestRepository.findBookByClientId(this.client.getId());
        assertNotNull(returnedLikedBookDTO);
        assertEquals(1, returnedLikedBookDTO.size());
        assertEquals(expectedResult, returnedLikedBookDTO);
    }

    @Test
    void shouldfindLikedBookByInexistantClientId() {
        List<LikedBookDTO> returnedLikedBookDTO = underTestRepository.findBookByClientId(inexistantClientId);
        assertNotNull(returnedLikedBookDTO);
        assertTrue(returnedLikedBookDTO.isEmpty());
    }

    @Test
    void shouldfindBookTrackByExistantClientId() {
        List<BookTrackDTO> expectedResult = List.of(new BookTrackDTO(this.track.getId(), this.track.getBookId(), this.track.getStatus()));
        List<BookTrackDTO> returnedBookTrackDTO = underTestRepository.findBookTrackByClientId(this.client.getId());
        assertNotNull(returnedBookTrackDTO);
        assertEquals(1, returnedBookTrackDTO.size());
        assertEquals(expectedResult, returnedBookTrackDTO);
    }

    @Test
    void shouldfindBookTrackByInexistantClientId() {
        List<BookTrackDTO> returnedBookTrackDTO = underTestRepository.findBookTrackByClientId(inexistantClientId);
        assertNotNull(returnedBookTrackDTO);
        assertTrue(returnedBookTrackDTO.isEmpty());
    }

    @Test
    void shouldfindRatingByExistantClientId() {
        List<RatingDTO> expectedResult = List.of(new RatingDTO(this.rating.getId(), this.rating.getRate(), this.rating.getBookId()));
        List<RatingDTO> returnedRatingDTO = underTestRepository.findRatingByClientId(this.client.getId());
        assertNotNull(returnedRatingDTO);
        assertEquals(1, returnedRatingDTO.size());
        assertEquals(expectedResult, returnedRatingDTO);
    }

    @Test
    void shouldfindRatingByInexistantClientId() {
        List<RatingDTO> returnedRatingDTO = underTestRepository.findRatingByClientId(inexistantClientId);
        assertNotNull(returnedRatingDTO);
        assertTrue(returnedRatingDTO.isEmpty());
    }

    @Test
    void shouldfindTrackedBooksIdByExistantClientId() {
        List<String> expectedList = List.of(this.track.getBookId());
        List<String> returnedList = underTestRepository.findTrackedBooksIdByClientId(this.client.getId());
        assertNotNull(returnedList);
        assertEquals(1, returnedList.size());
        assertEquals(expectedList, returnedList);
    }

    @Test
    void shouldfindTrackedBooksIdByInexistantClientId() {
        List<String> returnedList = underTestRepository.findTrackedBooksIdByClientId(inexistantClientId);
        assertNotNull(returnedList);
        assertTrue(returnedList.isEmpty());
    }

    @Test
    void shouldfindLikedBooksIdByExistantClientId() {
        List<String> expectedList = List.of(this.likedBook.getBookId());
        List<String> returnedList = underTestRepository.findLikedBooksIdByClientId(this.client.getId());
        assertNotNull(returnedList);
        assertEquals(1, returnedList.size());
        assertEquals(expectedList, returnedList);
    }

    @Test
    void shouldfindLikedBooksIdByInexistantClientId() {
        List<String> returnedList = underTestRepository.findLikedBooksIdByClientId(inexistantClientId);
        assertNotNull(returnedList);
        assertTrue(returnedList.isEmpty());
    }
}