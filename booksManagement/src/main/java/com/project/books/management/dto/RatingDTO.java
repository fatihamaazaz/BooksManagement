package com.project.books.management.dto;


import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingDTO {
    private Long id;
    @Max(5)
    @Min(0)
    private double rate;
    private String bookId;
}
