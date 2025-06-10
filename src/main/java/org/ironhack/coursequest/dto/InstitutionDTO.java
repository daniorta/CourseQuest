package org.ironhack.coursequest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class InstitutionDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotBlank
    private String email;

    @NotNull
    private int phoneNumber;

}
