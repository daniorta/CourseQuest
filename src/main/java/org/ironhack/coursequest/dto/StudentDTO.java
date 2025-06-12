package org.ironhack.coursequest.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class StudentDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    private String username;

    @NotBlank
    private String address;

    @NotNull(message = "Date of Birth cannot be null")
    private LocalDate dateOfBirth; //fecha de nacimiento

    @NotBlank
    private String mail;


}
