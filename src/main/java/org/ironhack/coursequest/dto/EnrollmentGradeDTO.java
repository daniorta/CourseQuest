package org.ironhack.coursequest.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentGradeDTO {

    @Min(0)
    @Max(100)
//    @NotNull(message = "Enrollment grade cannot be null.")
    private Double grade;
}
