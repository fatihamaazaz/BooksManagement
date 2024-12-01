package com.project.books.management.repositories;

import com.project.books.management.entities.LikedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface LikedBookRepository extends JpaRepository<LikedBook, Long> {
    @Transactional
    void deleteLikedBookById(Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM LikedBook L WHERE L.client.id = :clientId AND L.bookId = :id")
    Optional<Void> deleteLikedBookByBookId(Long clientId, String id);
}