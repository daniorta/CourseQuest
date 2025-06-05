package org.ironhack.coursequest.controller;

import jakarta.validation.Valid;
import org.ironhack.coursequest.dto.StudentDTO;
import org.ironhack.coursequest.model.Student;
import org.ironhack.coursequest.service.StudentService;
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
    public List<Student> getAllStudent(){
        return studentService.getAllStudent();
    }

    @GetMapping("/{id}")
    public Student getStudentId(@Valid @PathVariable Long id){
        return studentService.getById(id);
    }

    @PostMapping
    public Student createStudent(@Valid @RequestBody StudentDTO studentDTO){
        return studentService.createStudent(studentDTO);
    }

    @PutMapping("/{id}")
    private Student update(@Valid @PathVariable Long id, @Valid @RequestBody StudentDTO studentDTO){
        return studentService.updateStudent(id, studentDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @PathVariable Long id){
        studentService.delete(id);
    }

}
