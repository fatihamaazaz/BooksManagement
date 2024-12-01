package com.project.books.management.repositories;

import com.project.books.management.dto.BookTrackDTO;
import com.project.books.management.dto.LikedBookDTO;
import com.project.books.management.dto.RatingDTO;
import com.project.books.management.entities.Client;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findClientById(Long id);

    @Transactional
    void deleteClientById(Long clientId);

    @Query("SELECT new com.project.books.management.dto.LikedBookDTO(LB.id, LB.bookId) FROM LikedBook LB JOIN LB.client C WHERE C.id = :clientId")
    List<LikedBookDTO> findBookByClientId(Long clientId);

    @Query("SELECT new com.project.books.management.dto.BookTrackDTO(BT.id, BT.bookId, BT.status) FROM BookTrack BT JOIN BT.client C WHERE C.id = :id")
    List<BookTrackDTO> findBookTrackByClientId(Long id);


    @Query("SELECT new com.project.books.management.dto.RatingDTO(R.id, R.rate, R.bookId) FROM Rating R Join R.client C WHERE C.id = :id")
    List<RatingDTO> findRatingByClientId(Long id);

    Optional<Client> findClientByUserName(String userName);

    @Query("SELECT B.bookId FROM BookTrack B JOIN B.client C WHERE C.id = :clientId ")
    List<String> findTrackedBooksIdByClientId(Long clientId);

    @Query("SELECT L.bookId FROM LikedBook L JOIN L.client C WHERE C.id = :clientId ")
    List<String> findLikedBooksIdByClientId(Long clientId);
}