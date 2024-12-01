package com.project.books.management.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"clientId", "bookId"})
)
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Max(5)
    @Min(0)
    private double rate;
    @NotBlank
    private String bookId;
    @ManyToOne
    @JoinColumn(name = "clientId")
    private Client client;

    public Rating(String bookId, double rate, Client client){
        this.bookId = bookId;
        this.rate = rate;
        this.client = client;
    }
}
