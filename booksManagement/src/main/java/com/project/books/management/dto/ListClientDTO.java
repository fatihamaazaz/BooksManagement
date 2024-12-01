package com.project.books.management.dto;

import com.project.books.management.entities.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListClientDTO {
    private String userName;
    private String email;
    private String password;
    private LocalDate birthDate;
    @Enumerated(EnumType.STRING)
    private Role role;
}
