package com.project.books.management.mapping;

import com.project.books.management.entities.BookTrack;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

@Mapper
@RequiredArgsConstructor
public class BookTrackMapping {
    private final ModelMapper modelMapper;

    public <T> BookTrack mapTobookTrack(T bookTrack){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(bookTrack, BookTrack.class);
    }

    public <T> T mapFromBookTrack(BookTrack bookTrack, Class<T> targetClass) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(bookTrack, targetClass);
    }
}
