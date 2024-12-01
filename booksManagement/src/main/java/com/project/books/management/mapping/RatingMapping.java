package com.project.books.management.mapping;


import com.project.books.management.entities.Rating;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

@Mapper
@RequiredArgsConstructor
public class RatingMapping {

    private final ModelMapper modelMapper;

    public <T> Rating mapToRating(T rating){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(rating, Rating.class);
    }

    public <T> T mapFromRating(Rating rating, Class<T> targetClass) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(rating, targetClass);
    }
}
