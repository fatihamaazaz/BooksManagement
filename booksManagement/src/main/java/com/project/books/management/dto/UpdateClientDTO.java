package com.project.books.management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateClientDTO {
    @NotNull
    private String userName;
    @Email
    @NotNull
    private String email;
    @NotNull
    private String password;
    private LocalDate birthDate;
}
