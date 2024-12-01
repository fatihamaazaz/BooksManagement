package com.project.books.management.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(
        uniqueConstraints = @UniqueConstraint( columnNames = {"clientId", "bookId"})
)
public class LikedBook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String bookId;

    @ManyToOne
    @JoinColumn(name = "clientId")
    private Client client;

    public LikedBook(String bookId, Client client){
        this.bookId = bookId;
        this.client = client;
    }
}
