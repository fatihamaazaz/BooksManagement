package com.project.books.management.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@SequenceGenerator(name="employee_id_seq", initialValue=5, allocationSize=100)
public class Client {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="employee_id_seq")
    private Long id;
    @NotBlank
    @Column(unique = true)
    private String userName;
    @Email
    @Column(unique = true)
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @DateTimeFormat(style = "yyyy-MM-dd")
    private LocalDate birthDate;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "client")
    private List<Rating> ratings;

    @OneToMany(mappedBy = "client")
    private List<LikedBook> likedBooks;

    @OneToMany(mappedBy = "client")
    private List<BookTrack> booksTracks;

    public Client(String username, String email, String password, LocalDate birthDate, Role role){
        this.userName = username;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.role = role;
    }
}
