package com.project.books.management.repositories;

import com.project.books.management.dto.RatingDTO;
import com.project.books.management.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    @Transactional
    void deleteRatingById(Long id);


    @Query("SELECT AVG(R.rate) FROM Rating R WHERE R.bookId = :bookId")
    Optional<Double> findAVGRatingByBookId(String bookId);

    @Query("SELECT new com.project.books.management.dto.RatingDTO(R.id, R.rate, R.bookId) FROM Rating R WHERE R.bookId = :bookId")
    List<RatingDTO> findRatingsByBookId(String bookId);

    @Query("SELECT R FROM Rating R JOIN R.client C WHERE C.id = :clientId AND R.bookId = :bookId")
    List<Rating> findRatingByBookAndClientId(Long clientId, String bookId);

    Optional<Rating> findRatingById(Long id);
}