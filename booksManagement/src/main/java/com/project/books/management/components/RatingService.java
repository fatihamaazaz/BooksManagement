package com.project.books.management.components;

import com.project.books.management.dto.RatingDTO;
import com.project.books.management.entities.Rating;
import com.project.books.management.exceptions.BookNotRatedException;
import com.project.books.management.exceptions.RatingNotFoundException;
import com.project.books.management.mapping.RatingMapping;
import com.project.books.management.repositories.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final RatingMapping ratingMapping;

    String msgRatingNotFoundException = "Rating of id %d not found";
    private final ModelMapper modelMapper;

    public RatingDTO addRating(Rating rating){
        return ratingMapping.mapFromRating(ratingRepository.save(rating), RatingDTO.class);
    }

    public RatingDTO updateRating(RatingDTO ratingDTO){
        Long id = ratingDTO.getId();
        Rating rating = ratingRepository.findRatingById(id)
                .orElseThrow(()-> new RatingNotFoundException(String.format(msgRatingNotFoundException, id)));
        rating.setRate(ratingDTO.getRate());
        return ratingMapping.mapFromRating(ratingRepository.save(rating), RatingDTO.class);
    }

    public void deleteRating(Long id){
        ratingRepository.deleteRatingById(id);
    }

    public Double getAVGRatingByBookId(String bookId){
        Double rate = ratingRepository.findAVGRatingByBookId(bookId).orElseThrow(
                () -> new BookNotRatedException(String.format("Book of id %s Not rated", bookId)));
        if (rate == null) {
            return 0.0; // ou lancer une exception
        }
        return Math.round(rate * 10.0) / 10.0;
    }

    public List<RatingDTO> getRatingByClientIdAndBookId(Long clientId, String bookId){
        return ratingRepository.findRatingByBookAndClientId(clientId, bookId).stream()
                .map(r->ratingMapping.mapFromRating(r, RatingDTO.class)).collect(Collectors.toList());
    }

    public List<RatingDTO> getRatingByBookId(String bookId){
        return ratingRepository.findRatingsByBookId(bookId);
    }

    public List<RatingDTO> getAllRating(){
        return ratingRepository.findAll().stream().map((element) -> modelMapper.map(element, RatingDTO.class))
                .collect(Collectors.toList());
    }

}
