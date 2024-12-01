package com.project.books.management.repositories;

import com.project.books.management.entities.Client;
import com.project.books.management.entities.LikedBook;
import com.project.books.management.entities.Role;
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
class LikedBookRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private LikedBookRepository underTestRepository;

    Client client;
    LikedBook likedBook;

    String unlikedBookId = "fffff";

    @BeforeEach
    void setUp(){
        this.client = clientRepository.save(new Client("fm", "f@gmail.com","pass",
                LocalDate.of(2000,1,1), Role.USER));
        this.likedBook = underTestRepository.save(new LikedBook("gtrffs", client));
    }


    @Test
    void shouldDeleteLikedBookByExistingBookId() {
        List<LikedBook> initialTracks = underTestRepository.findAll();
        assertEquals(1, initialTracks.size());

        underTestRepository.deleteLikedBookByBookId(this.client.getId(), this.likedBook.getBookId());

        List<LikedBook> likedBooks = underTestRepository.findAll();
        assertNotNull(likedBooks);
        assertTrue(likedBooks.isEmpty());
    }

    @Test
    void shouldDeleteLikedBookByUnexistingBookId() {
        List<LikedBook> initialTracks = underTestRepository.findAll();
        assertEquals(1, initialTracks.size());

        underTestRepository.deleteLikedBookByBookId(this.client.getId(), unlikedBookId);

        List<LikedBook> likedBooks = underTestRepository.findAll();
        assertNotNull(likedBooks);
        assertEquals(1, likedBooks.size());
    }

}