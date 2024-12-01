package com.project.books.management.repositories;

import com.project.books.management.entities.*;
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
class BookTrackRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BookTrackRepository underTestRepository;

    Client client;
    BookTrack track;

    String untrackedBookId = "fffff";

    @BeforeEach
    void setUp(){
        this.client = clientRepository.save(new Client("fm", "f@gmail.com","pass",
                LocalDate.of(2000,1,1), Role.USER));
        this.track = underTestRepository.save(new BookTrack("gtrffs", client));
    }


    @Test
    void shouldfindTrackIdByTrackedBookId() {
        Optional<Long> trackId = underTestRepository.findTrackIdByBookId(this.client.getId(),this.track.getBookId());
        assertNotNull(trackId);
        assertEquals(track.getId(), trackId.get());
    }

    @Test
    void shouldfindTrackIdByUntrackedBookId() {
        Optional<Long> trackId = underTestRepository.findTrackIdByBookId(this.client.getId(),untrackedBookId);
        assertNotNull(trackId);
        assertTrue(trackId.isEmpty());
    }

    @Test
    void deleteTrackByTrackedBookId() {
        List<BookTrack> initialTracks = underTestRepository.findAll();
        assertEquals(1, initialTracks.size());

        underTestRepository.deleteTrackByBookId(this.client.getId(), this.track.getBookId());

        List<BookTrack> tracks = underTestRepository.findAll();
        assertNotNull(tracks);
        assertTrue(tracks.isEmpty());
    }

    @Test
    void deleteTrackByuntrackedBookId() {
        List<BookTrack> initialTracks = underTestRepository.findAll();
        assertEquals(1, initialTracks.size());

        underTestRepository.deleteTrackByBookId(this.client.getId(), untrackedBookId);

        List<BookTrack> tracks = underTestRepository.findAll();
        assertNotNull(tracks);
        assertEquals(1, tracks.size());
    }

}