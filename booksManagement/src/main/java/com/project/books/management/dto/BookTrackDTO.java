package com.project.books.management.dto;

import com.project.books.management.entities.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookTrackDTO {
    private Long id;
    @NonNull
    private String bookId;
    @Enumerated(EnumType.STRING)
    private Status status;
}
 