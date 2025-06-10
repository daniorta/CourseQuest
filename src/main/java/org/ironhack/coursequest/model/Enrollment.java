package org.ironhack.coursequest.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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

@Entity
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Enrollment Date cannot be null.")
    private LocalDate enrollmentDate; //Fecha de inscripción

    @NotNull(message = "Enrollment status cannot be null.")
    private Status status;

    @Min(0)
    @Max(100)
    @NotNull(message = "Enrollment grade cannot be null.")
    private Double grade;

    @NotNull(message = "Enrollment ending cannot be null.")
    private LocalDate ending;//Fecha de finalización

    @ManyToOne
    @JoinColumn(name="course_id")
    @JsonIgnore
    private Course course;


    @ManyToOne
    @JoinColumn(name="student_id")
    @JsonIgnore
    private Student student;

}
