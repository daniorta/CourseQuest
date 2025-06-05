package org.ironhack.coursequest.controller;

import jakarta.validation.Valid;
import org.ironhack.coursequest.dto.CourseDTO;
import org.ironhack.coursequest.model.Course;
import org.ironhack.coursequest.service.CourseService;
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
    public List<Course> getAllCourse(){
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public Course getCourseId(@PathVariable Long id){
      return courseService.getById(id);
    }

    @PostMapping
    public Course newCourse(@Valid @RequestBody CourseDTO courseDTO){
        Course course = courseService.createCourse(courseDTO);
        return course ;
    }

    @PutMapping("/{id}")
    public Course update(@Valid @PathVariable Long id, @Valid @RequestBody CourseDTO courseDTO){
        return courseService.updateCourse(id, courseDTO);

    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @PathVariable Long id) {
         courseService.deleteCourse(id);
    }
}
