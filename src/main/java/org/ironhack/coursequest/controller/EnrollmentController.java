package org.ironhack.coursequest.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import org.ironhack.coursequest.dto.EnrollmentDTO;
import org.ironhack.coursequest.dto.EnrollmentGradeDTO;
import org.ironhack.coursequest.model.Enrollment;
import org.ironhack.coursequest.service.EnrollmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("enrollment")
public class EnrollmentController {

    //Inyección de dependencias.
    private final EnrollmentService enrollmentService;
    public EnrollmentController(EnrollmentService enrollmentService){
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public ResponseEntity<List<Enrollment>> allEnrollment(){
        List<Enrollment> enrollments = enrollmentService.getAllEnrollment();
        return ResponseEntity.ok(enrollments);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Enrollment> getEnrollmentById(@Valid @PathVariable Long id){
        Enrollment enrollment = enrollmentService.getById(id);
        return ResponseEntity.ok(enrollment);
    }


    @PostMapping
    public ResponseEntity<Enrollment> createEnrollment(@Valid @RequestBody EnrollmentDTO enrollmentDTO){
        Enrollment enrollment = enrollmentService.createEnrollment(enrollmentDTO);
        return new ResponseEntity<>(enrollment, HttpStatus.CREATED); // 201 Created
    }



    @PutMapping("/{id}")
    public ResponseEntity<Enrollment> updateEnrollment(@Valid @PathVariable Long id, @Valid @RequestBody EnrollmentDTO enrollmentDTO){
        Enrollment enrollment = enrollmentService.updateEnrollment(id, enrollmentDTO);
        return ResponseEntity.ok(enrollment);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Enrollment> updateGrade(@Valid @PathVariable Long id, @RequestBody EnrollmentGradeDTO enrollmentGradeDTO){
        Enrollment enrollment = enrollmentService.updateGradeEnrollment(id, enrollmentGradeDTO);
        return ResponseEntity.ok(enrollment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(@Valid @PathVariable Long id){
       enrollmentService.deleteEnrollment(id);
       return ResponseEntity.noContent().build();
    }

    @GetMapping("/course")
    public ResponseEntity<List<Enrollment>> getEnrollmentsByCourse(@RequestParam Long courseId){
       List<Enrollment> enrollment = enrollmentService.getEnrollmentsByCourse(courseId);
       return ResponseEntity.ok(enrollment);
    }

    @GetMapping("/student")
    public  ResponseEntity<List<Enrollment>> getEnrollmentsByStudent(@RequestParam Long studentId){
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudent(studentId);
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<Optional<Enrollment>> getEnrollmentByStudentAndCourse(@Valid @PathVariable Long studentId,@Valid @PathVariable Long courseId){
       Optional<Enrollment> enrollment = enrollmentService.getEnrollmentByStudentAndCourse(studentId, courseId);
        return ResponseEntity.ok(enrollment);
    }


}
