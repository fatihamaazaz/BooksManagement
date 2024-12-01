package com.project.books.management.components;


import com.project.books.management.dto.BookTrackDTO;
import com.project.books.management.entities.BookTrack;
import com.project.books.management.entities.Client;
import com.project.books.management.entities.LikedBook;
import com.project.books.management.exceptions.*;
import com.project.books.management.mapping.BookTrackMapping;
import com.project.books.management.repositories.BookTrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookTrackService {
    final private BookTrackRepository bookTrackRepository;
    final private BookTrackMapping bookTrackMapping;

    String exceptionMsg = "book track of id %d not found";

    public BookTrackDTO addBookTrack(BookTrack bookTrack){
        return bookTrackMapping.mapFromBookTrack(bookTrackRepository.save(bookTrack), BookTrackDTO.class);
    }

    public List<BookTrackDTO> getAllBookTrack(){
        return bookTrackRepository.findAll().stream()
                .map(b -> bookTrackMapping.mapFromBookTrack(b, BookTrackDTO.class))
                .collect(Collectors.toList());
    }

    public BookTrackDTO updateBookTrack(BookTrackDTO bookTrackDTO){
        Long id = bookTrackDTO.getId();
        BookTrack bookTrack = bookTrackRepository.findById(id).orElseThrow(
                () -> new BookTrackNotFoundException(String.format(exceptionMsg, id))
        );
        bookTrack.setStatus(bookTrackDTO.getStatus());
        return bookTrackMapping.mapFromBookTrack(bookTrackRepository.save(bookTrack), BookTrackDTO.class);
    }

    @Transactional
    public void deleteBookTrack(Long id, Client client){
        BookTrack bookTrack = bookTrackRepository.findById(id).orElseThrow(
                () -> new BookTrackNotFoundException(String.format(exceptionMsg, id)));
        if(bookTrack.getClient() != client){
            throw new BookTrackNotAccessibleException("you don't have the right to delete the record of id "+ id);
        }
        bookTrackRepository.deleteById(id);
    }

    public Long getTrackIdByBookId(Long clientId, String id) {
        return bookTrackRepository.findTrackIdByBookId(clientId, id)
                .orElseThrow(
                        () -> new BookNotTrackedException(String.format("Book of %s Not Tracked", id)));
    }

    @Transactional
    public void deleteTrackByBookId(Long clientId, String id) {
        bookTrackRepository.deleteTrackByBookId(clientId, id).orElseThrow(
                () -> new BookNotTrackedException(String.format("Book of %s Not Tracked", id)));
    }
}
