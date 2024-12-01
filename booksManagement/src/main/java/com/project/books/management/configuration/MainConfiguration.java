package com.project.books.management.configuration;


import com.project.books.management.mapping.BookTrackMapping;
import com.project.books.management.mapping.ClientMapping;
import com.project.books.management.mapping.LikedBookMapping;
import com.project.books.management.mapping.RatingMapping;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public BookTrackMapping bookTrackMapping(){
        return new BookTrackMapping(modelMapper());
    }

    @Bean
    public ClientMapping clientMapping(){
        return new ClientMapping(modelMapper());
    }

    @Bean
    public LikedBookMapping likedBookMapping(){
        return new LikedBookMapping(modelMapper());
    }

    @Bean
    public RatingMapping ratingMapping(){
        return new RatingMapping(modelMapper());
    }
}