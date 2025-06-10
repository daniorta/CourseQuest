package org.ironhack.coursequest.service;

import org.ironhack.coursequest.dto.CourseDTO;
import org.ironhack.coursequest.exception.BadRequestException;
import org.ironhack.coursequest.exception.NotFoundException;
import org.ironhack.coursequest.model.Course;
import org.ironhack.coursequest.repository.CourseRepository;
import org.springframework.stereotype.Service;

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
        return getCourse(id);
    }

    public Course createCourse(CourseDTO courseDTO){

        validateName(courseDTO.getName());

        Course newCourse = new Course();
        newCourse.setName(courseDTO.getName());
        newCourse.setDuration(courseDTO.getDuration());
        newCourse.setCapacity(courseDTO.getCapacity());
        newCourse.setTutor(courseDTO.getTutor());

        Course saveCourse = courseRepository.save(newCourse);

        return saveCourse;
    }

    public Course updateCourse(Long id, CourseDTO courseDTO ){

        validateName(courseDTO.getName());

        Course courseFromDb = getCourse(id);//Obtenemos el objeto mediante el get

        courseFromDb.setName(courseDTO.getName());
        courseFromDb.setDuration(courseDTO.getDuration());
        courseFromDb.setCapacity(courseDTO.getCapacity());
        courseFromDb.setTutor(courseDTO.getTutor());

        validateName(courseDTO.getName());

        Course saveteCourse = courseRepository.save(courseFromDb);

        return saveteCourse;
    }



    //No podemos eliminar el curso ya que tiene datos, de inscripciones, matrículas
    public void deleteCourse(Long id) {
        boolean existsById = courseRepository.existsById(id);

        if (!existsById) {
            throw new NotFoundException(id);
        } else {
            courseRepository.deleteById(id);
        }
    }


    //Metodo para redundancia de busqueda por id.
    public Course getCourse(Long id){
        return courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    //Extraemos el método para ver si existe ese nombre al crear o actualizar.
    private void validateName(String name){
        boolean existsByName = courseRepository.existsByName(name);
        if(existsByName){
            throw new BadRequestException("Course with name " + name + " already exists.");
        }
    }

}
