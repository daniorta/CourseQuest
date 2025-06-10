package org.ironhack.coursequest.controller;

import jakarta.validation.Valid;
import org.ironhack.coursequest.dto.CourseDTO;
import org.ironhack.coursequest.model.Course;
import org.ironhack.coursequest.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("course")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourse(){
       List<Course> courses = courseService.getAllCourses();
       return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id){
      Course course = courseService.getById(id);
      return ResponseEntity.ok(course);
    }

    @PostMapping
    public ResponseEntity<Course> newCourse(@Valid @RequestBody CourseDTO courseDTO){
        Course course = courseService.createCourse(courseDTO);
        return new  ResponseEntity<>(course,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> update(@Valid @PathVariable Long id, @Valid @RequestBody CourseDTO courseDTO){
        Course course = courseService.updateCourse(id, courseDTO);
        return  ResponseEntity.ok(course);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Valid @PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
