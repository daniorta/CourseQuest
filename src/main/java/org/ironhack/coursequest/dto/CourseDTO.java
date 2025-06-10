package org.ironhack.coursequest.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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

    @Min(0)
    @Max(3000)
    @NotNull
    private Integer duration;

    @NotNull
    @Min(1)
    @Max(15)
    private Integer capacity;

    @NotBlank
    private String tutor;

}
