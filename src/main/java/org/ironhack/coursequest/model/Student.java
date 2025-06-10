package org.ironhack.coursequest.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Student extends User{


    @NotBlank
    private String surname;

    @NotBlank
    private String address;

    @NotNull(message = "Date of Birth cannot be null")
    private LocalDate dateOfBirth; //fecha de nacimiento


    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Enrollment> enrollmentList = new ArrayList<>();


//todo quitar name y mail y ponerlo en userService para que hereden



}
