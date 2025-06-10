package org.ironhack.coursequest.repository;

import org.ironhack.coursequest.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    //Traer todas las inscripciones de un curso especifico
    List<Enrollment> findByCourseId(Long courseId);

    //Obtenemos las inscripciones de un estudiante.
    List<Enrollment> findByStudentId(Long studentId);

    //Inscripciones de un estudiante en un curso
    Optional<Enrollment> findByStudentIdAndCourseId(Long studentId, Long COurseId);


}
