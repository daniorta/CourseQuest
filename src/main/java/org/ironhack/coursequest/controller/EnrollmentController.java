package org.ironhack.coursequest.controller;

import jakarta.validation.Valid;
import org.ironhack.coursequest.dto.EnrollmentDTO;
import org.ironhack.coursequest.dto.EnrollmentGradeDTO;
import org.ironhack.coursequest.model.Enrollment;
import org.ironhack.coursequest.service.EnrollmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("enrollment")
public class EnrollmentController {

    //Inyección de dependencias.
    private final EnrollmentService enrollmentService;
    public EnrollmentController(EnrollmentService enrollmentService){
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public List<Enrollment> allEnrollment(){
        return enrollmentService.getAllEnrollment();
    }

    @GetMapping("/{id}")
    public Enrollment getEnrollmentId(@Valid @PathVariable Long id){
        return enrollmentService.getById(id);
    }

    @PostMapping
    public Enrollment createEnrollment(@Valid @RequestBody EnrollmentDTO enrollmentDTO){
        Enrollment enrollment = enrollmentService.createEnrollment(enrollmentDTO);
        return enrollment;
    }



    @PutMapping("/{id}")
    public Enrollment updateEnrollment(@Valid @PathVariable Long id, @Valid @RequestBody EnrollmentDTO enrollmentDTO){
        return enrollmentService.updateEnrollment(id, enrollmentDTO);
    }

    @PatchMapping("/{id}")
    public Enrollment updateGrade(@Valid @PathVariable Long id, @RequestBody EnrollmentGradeDTO enrollmentGradeDTO){
        return enrollmentService.updateGradeEnrollment(id, enrollmentGradeDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteEnrollment(@Valid @PathVariable Long id){
        enrollmentService.deleteEnrollment(id);

    }


}
