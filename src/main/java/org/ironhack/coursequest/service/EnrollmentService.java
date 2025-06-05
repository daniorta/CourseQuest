package org.ironhack.coursequest.service;

import org.ironhack.coursequest.dto.EnrollmentDTO;
import org.ironhack.coursequest.dto.EnrollmentGradeDTO;
import org.ironhack.coursequest.model.Course;
import org.ironhack.coursequest.model.Enrollment;
import org.ironhack.coursequest.model.Student;
import org.ironhack.coursequest.repository.CourseRepository;
import org.ironhack.coursequest.repository.EnrollmentRepository;
import org.ironhack.coursequest.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {

    //Inyeción de dependencias.
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, CourseRepository courseRepository, StudentRepository studentRepository){
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    public List<Enrollment> getAllEnrollment(){
        return enrollmentRepository.findAll();
    }

    public Enrollment getById(Long id){
        Optional<Enrollment> optionalEnrollment = getEnrollment(id);

        return optionalEnrollment.get();
    }

    public Enrollment createEnrollment(EnrollmentDTO enrollmentDTO){
        Enrollment newEnrollment = new Enrollment();

        newEnrollment.setEnrollmentDate(enrollmentDTO.getEnrollmentDate());
        newEnrollment.setStatus(enrollmentDTO.getStatus());
        newEnrollment.setGrade(enrollmentDTO.getGrade());
        newEnrollment.setEnding(enrollmentDTO.getEnding());

        //Relacionar la matricula con el curso y con el studiante.
        Optional <Course> optionalCourse = courseRepository.findById(enrollmentDTO.getCourseId());

        if(optionalCourse.isPresent()){
            newEnrollment.setCourse(optionalCourse.get());
        }else {
            System.out.println("Course not found.");
            return null;
        }

        Optional<Student> optionalStudent = studentRepository.findById(enrollmentDTO.getStudentId());

        if(optionalStudent.isPresent()){
            newEnrollment.setStudent(optionalStudent.get());
        }else {
            System.out.println("Studend not found.");
            return null;
        }

        Enrollment saveEnrollment = enrollmentRepository.save(newEnrollment);

        return saveEnrollment;
    }

    public Enrollment updateEnrollment(Long id, EnrollmentDTO enrollmentDTO){
        Optional<Enrollment> optionalEnrollment = getEnrollment(id);

        if(!optionalEnrollment.isPresent()){
            return null;
        }
        Enrollment enrollmentFronDb = optionalEnrollment.get();

        enrollmentFronDb.setEnrollmentDate(enrollmentDTO.getEnrollmentDate());
        enrollmentFronDb.setStatus(enrollmentDTO.getStatus());
        enrollmentFronDb.setGrade(enrollmentDTO.getGrade());
        enrollmentFronDb.setEnding(enrollmentDTO.getEnding());


        Optional<Course> optionalCourse = courseRepository.findById(enrollmentDTO.getCourseId());

        if(optionalCourse.isPresent()){
            enrollmentFronDb.setCourse(optionalCourse.get());
        }else {
            System.out.println("Course id not found.");
            return null;
        }

        Optional<Student> optionalStudent = studentRepository.findById(enrollmentDTO.getStudentId());

                if(optionalStudent.isPresent()){
                    enrollmentFronDb.setStudent(optionalStudent.get());
                }else {
                    System.out.println("Studen id not found");
                    return null;
                }
        Enrollment saveEnrollment = enrollmentRepository.save(enrollmentFronDb);

        return saveEnrollment;
    }


    public Enrollment updateGradeEnrollment(Long id, EnrollmentGradeDTO enrollmentGradeDTO){
        Optional<Enrollment> optionalEnrollment = getEnrollment(id);

        Enrollment enrollmentFromDb = optionalEnrollment.get();
        enrollmentFromDb.setGrade(enrollmentGradeDTO.getGrade());

        Enrollment saveEnrollmentFromDb = enrollmentRepository.save(enrollmentFromDb);

        return saveEnrollmentFromDb;

    }

    public void deleteEnrollment(Long id){
        boolean existsById = enrollmentRepository.existsById(id);

        if(!existsById){
            System.out.println("Enrollment Id not found.");

        }else {
            enrollmentRepository.deleteById(id);
        }
    }



    //Método para redundancia de busqueda por id.
    public Optional<Enrollment> getEnrollment(Long id){
        Optional<Enrollment>optionalEnrollment = enrollmentRepository.findById(id);

        if(optionalEnrollment.isEmpty()){
            System.out.println("Id no found");
        }
        return optionalEnrollment;
    }


}
