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

public class CourseDTO {

    @NotBlank
    private String name;

    @NotNull
    private Integer duration;

    @NotBlank
    private String tutor;

}
