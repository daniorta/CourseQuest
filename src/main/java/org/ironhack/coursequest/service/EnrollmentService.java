package org.ironhack.coursequest.service;

import org.ironhack.coursequest.dto.EnrollmentDTO;
import org.ironhack.coursequest.dto.EnrollmentGradeDTO;
import org.ironhack.coursequest.exception.BadRequestException;
import org.ironhack.coursequest.exception.NotFoundException;
import org.ironhack.coursequest.model.Course;
import org.ironhack.coursequest.model.Enrollment;
import org.ironhack.coursequest.model.Student;
import org.ironhack.coursequest.repository.CourseRepository;
import org.ironhack.coursequest.repository.EnrollmentRepository;
import org.ironhack.coursequest.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        return getEnrollment(id);
    }

    public Enrollment createEnrollment(EnrollmentDTO enrollmentDTO){

        Enrollment newEnrollment = new Enrollment();
        newEnrollment.setEnrollmentDate(enrollmentDTO.getEnrollmentDate());
        newEnrollment.setStatus(enrollmentDTO.getStatus());
        newEnrollment.setGrade(enrollmentDTO.getGrade());
        newEnrollment.setEnding(enrollmentDTO.getEnding());

        //Relacionar la matricula con el curso y con el studiante.
        Course course = courseRepository.findById(enrollmentDTO.getCourseId())
               .orElseThrow(() -> new BadRequestException("Course ID " + enrollmentDTO.getCourseId() + " not found."));
        newEnrollment.setCourse(course);


        Student student = studentRepository.findById(enrollmentDTO.getStudentId())
                .orElseThrow(() -> new BadRequestException("Student ID " + enrollmentDTO.getStudentId() + " not found."));
        newEnrollment.setStudent(student);

        validateEnrollmentDTO(enrollmentDTO);
        Enrollment saveEnrollment = enrollmentRepository.save(newEnrollment);

        return saveEnrollment;
    }

    public Enrollment updateEnrollment(Long id, EnrollmentDTO enrollmentDTO){

        Enrollment enrollmentFromDb = getEnrollment(id);

        enrollmentFromDb.setEnrollmentDate(enrollmentDTO.getEnrollmentDate());
        enrollmentFromDb.setStatus(enrollmentDTO.getStatus());
        enrollmentFromDb.setGrade(enrollmentDTO.getGrade());
        enrollmentFromDb.setEnding(enrollmentDTO.getEnding());

        Course course = courseRepository.findById(enrollmentDTO.getCourseId())
                .orElseThrow(() -> new BadRequestException("Course ID " + enrollmentDTO.getCourseId() + " not found."));
        enrollmentFromDb.setCourse(course);

        Student student = studentRepository.findById(enrollmentDTO.getStudentId())
                .orElseThrow(() -> new BadRequestException("Student ID " + enrollmentDTO.getStudentId() + " not found."));
        enrollmentFromDb.setStudent(student);

        validateEnrollmentDTO(enrollmentDTO);
        Enrollment saveEnrollment = enrollmentRepository.save(enrollmentFromDb);

        return saveEnrollment;
    }


    public Enrollment updateGradeEnrollment(Long id, EnrollmentGradeDTO enrollmentGradeDTO){

        Enrollment enrollmentFromDb = getEnrollment(id);
        enrollmentFromDb.setGrade(enrollmentGradeDTO.getGrade());

        Enrollment saveEnrollmentFromDb = enrollmentRepository.save(enrollmentFromDb);

        return saveEnrollmentFromDb;

    }

    public void deleteEnrollment(Long id){
        boolean existsById = enrollmentRepository.existsById(id);

        if(!existsById){
            throw new NotFoundException(id);

        }else {
            enrollmentRepository.deleteById(id);
        }
    }


    public List<Enrollment> getEnrollmentsByCourse(Long courseId){
        return enrollmentRepository.findByCourseId(courseId);
    }

    public List<Enrollment> getEnrollmentsByStudent(Long studentId){
        return enrollmentRepository.findByStudentId(studentId);
    }

    public Optional<Enrollment> getEnrollmentByStudentAndCourse(Long studentId, Long courseId){
        return enrollmentRepository.findByStudentIdAndCourseId(studentId,courseId);
    }


    //Métodos para redundancia de busqueda por id.
    public Enrollment getEnrollment(Long id){
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    private void validateEnrollmentDTO(EnrollmentDTO enrollmentDTO) {

        if (enrollmentDTO.getEnrollmentDate().isAfter(LocalDate.now())){
            throw new BadRequestException("Enrollment date cannot be in the future.");
        }
        if(enrollmentDTO.getEnding().isAfter(LocalDate.now())){
            throw new BadRequestException("Ending date cannot be in the future.");
        }

    }




}
