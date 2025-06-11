package org.ironhack.coursequest.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ironhack.coursequest.enuns.Status;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class EnrollmentDTO {

    @NotNull(message = "Enrollment Date cannot be null.")
    private LocalDate enrollmentDate; //Fecha de inscripción

    @NotNull(message = "Enrollment status cannot be null.")
    private Status status;

//    @Min(0)
//    @Max(100)
//    @NotNull(message = "Enrollment grade cannot be null.")
//    private Double grade;


    @NotNull(message = "Enrollment ending cannot be null.")
    private LocalDate ending;//Fecha de finalización

    @NotNull(message = "Course ID cannot be null.")
    private Long courseId;

    @NotNull(message = "Student ID cannot be null.")
    private Long studentId;


}
