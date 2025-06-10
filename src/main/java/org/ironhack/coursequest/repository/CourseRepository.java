package org.ironhack.coursequest.repository;

import org.ironhack.coursequest.dto.CourseDTO;
import org.ironhack.coursequest.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    //Evitar crear un curso con el mismo nombre
    boolean existsByName(String name);


}
