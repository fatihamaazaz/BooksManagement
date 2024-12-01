package com.project.books.management.repositories;

import com.project.books.management.entities.BookTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface BookTrackRepository extends JpaRepository<BookTrack, Long> {
    @Query("SELECT B.id FROM BookTrack B JOIN B.client C WHERE C.id = :clientId AND B.bookId = :id ")
    Optional<Long> findTrackIdByBookId(Long clientId, String id);

    @Modifying
    @Transactional
    @Query("DELETE FROM BookTrack B WHERE B.client.id = :clientId AND B.bookId = :id")
    Optional<Void> deleteTrackByBookId(Long clientId, String id);
}