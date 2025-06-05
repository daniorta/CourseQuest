package org.ironhack.coursequest.service;

import org.ironhack.coursequest.dto.CourseDTO;
import org.ironhack.coursequest.model.Course;
import org.ironhack.coursequest.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }


    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getById(Long id){
        Optional<Course> optionalCourse = getCourse(id);
        return optionalCourse.get();
    }



    public Course createCourse(CourseDTO courseDTO){

        Course newCourse = new Course();
        newCourse.setName(courseDTO.getName());
        newCourse.setDuration(courseDTO.getDuration());
        newCourse.setTutor(courseDTO.getTutor());

        Course saveCourse = courseRepository.save(newCourse);

        return saveCourse;

    }

    public Course updateCourse(Long id, CourseDTO courseDTO ){
        Optional<Course> optionalCourse = getCourse(id);

        if(optionalCourse.isEmpty()){
            return null;
        }
        Course courseFromDb = optionalCourse.get();//Obtenemos el objeto mediante el get

        courseFromDb.setName(courseDTO.getName());
        courseFromDb.setDuration(courseDTO.getDuration());
        courseFromDb.setTutor(courseDTO.getTutor());

        Course updateCourse = courseRepository.save(courseFromDb);

        return updateCourse;
    }


    //No podemos eliminar el curso ya que tiene datos, de inscripciones, matrículas
    public void deleteCourse(Long id) {
        boolean existsById = courseRepository.existsById(id);

        if (!existsById) {
            System.out.println("Id not found.");
        } else {
            courseRepository.deleteById(id);
        }
    }


    //Metodo para redundancia de busqueda por id.
    public Optional<Course> getCourse(Long id){
        Optional<Course> optionalCourse = courseRepository.findById(id);

        if(optionalCourse.isEmpty()){
            System.out.println("Id not found.");
        }
        return optionalCourse;
    }

}
