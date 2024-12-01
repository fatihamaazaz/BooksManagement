package com.project.books.management.mapping;

import com.project.books.management.entities.Client;
import com.project.books.management.entities.LikedBook;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;


@Mapper
@RequiredArgsConstructor
public class LikedBookMapping {
    private final ModelMapper modelMapper;

    public <T> LikedBook mapLikedBook(T likedBook){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(likedBook, LikedBook.class);
    }

    public <T> T mapFromLikedBook(LikedBook likedBook, Class<T> targetClass) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(likedBook, targetClass);
    }

}
