package org.ironhack.coursequest.controller;

import jakarta.validation.Valid;
import org.ironhack.coursequest.dto.StudentDTO;
import org.ironhack.coursequest.model.Student;
import org.ironhack.coursequest.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("student")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudent(){
        List<Student> students = studentService.getAllStudent();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@Valid @PathVariable Long id){
        Student student = studentService.getById(id);
        return  ResponseEntity.ok(student);
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody StudentDTO studentDTO){
        Student student = studentService.createStudent(studentDTO);
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    private ResponseEntity<Student> update(@Valid @PathVariable Long id, @Valid @RequestBody StudentDTO studentDTO){
       Student student = studentService.updateStudent(id, studentDTO);
       return ResponseEntity.ok(student);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Valid @PathVariable Long id){
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
