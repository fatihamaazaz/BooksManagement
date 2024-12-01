package com.project.books.management.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"clientId", "bookId"})
)
@NoArgsConstructor
public class BookTrack {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String bookId;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    @JoinColumn(name = "clientId")
    private Client client;

    public BookTrack(String bookId, Client client){
        this.bookId = bookId;
        this.status = Status.BEGINNING;
        this.client = client;
    }
}
