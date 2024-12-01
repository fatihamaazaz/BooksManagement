package com.project.books.management.components;


import com.project.books.management.dto.LikedBookDTO;
import com.project.books.management.entities.Client;
import com.project.books.management.entities.LikedBook;
import com.project.books.management.exceptions.BookNotLikedException;
import com.project.books.management.exceptions.LikedBookNotAccesibleException;
import com.project.books.management.exceptions.LikedBookNotFoundException;
import com.project.books.management.mapping.LikedBookMapping;
import com.project.books.management.repositories.LikedBookRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikedBookService {
    private final LikedBookRepository likedBookRepository;
    private final LikedBookMapping likedBookMapping;

    public LikedBookDTO addLikedBook(LikedBook likedbook){
        return likedBookMapping.mapFromLikedBook(likedBookRepository.save(likedbook), LikedBookDTO.class);
    }

    public void deleteLikedBook(Long id, Client client){
        LikedBook likedBook = likedBookRepository.findById(id).orElseThrow(
                () -> new LikedBookNotFoundException("LikedBook record of id "+ id + " doesn't exist"));
        if(likedBook.getClient() != client){
            throw new LikedBookNotAccesibleException("you don't have the right to delete the record of id "+ id);
        }
        likedBookRepository.deleteLikedBookById(id);
    }

    public List<LikedBookDTO> getAllLikedBooks(){
        return likedBookRepository.findAll().stream().map(
                b -> likedBookMapping.mapFromLikedBook(b, LikedBookDTO.class))
                .collect(Collectors.toList());
    }

    public void deleteLikedBookByBookId(Long clientId, String id) {
        likedBookRepository.deleteLikedBookByBookId(clientId, id).orElseThrow(
                () -> new BookNotLikedException(String.format("Book of %s Not Liked", id)));
    }
}
