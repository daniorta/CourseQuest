package org.ironhack.coursequest.service;

import org.ironhack.coursequest.dto.StudentDTO;
import org.ironhack.coursequest.exception.BadRequestException;
import org.ironhack.coursequest.exception.NotFoundException;
import org.ironhack.coursequest.model.Student;
import org.ironhack.coursequest.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudent(){
        return studentRepository.findAll();
    }

    public Student getById(Long id){
        return getStudentId(id);
    }

    public Student createStudent(StudentDTO studentDTO){

        Student newStudent = new Student();
        newStudent.setName(studentDTO.getName());
        newStudent.setSurname(studentDTO.getSurname());
        newStudent.setAddress(studentDTO.getAddress());
        newStudent.setDateOfBirth(studentDTO.getDateOfBirth());
        newStudent.setEmail(studentDTO.getMail());

        validateStudentAge(studentDTO.getDateOfBirth());
        Student saveStudent = studentRepository.save(newStudent);

        return saveStudent;
    }

    public Student updateStudent(Long id, StudentDTO studentDTO){

        Student studentFromDb = getStudentId(id);

        studentFromDb.setName(studentDTO.getName());
        studentFromDb.setSurname(studentDTO.getSurname());
        studentFromDb.setAddress(studentDTO.getAddress());
        studentFromDb.setDateOfBirth(studentDTO.getDateOfBirth());
        studentFromDb.setEmail(studentDTO.getMail());

        validateStudentAge(studentDTO.getDateOfBirth());
        Student saveStudent = studentRepository.save(studentFromDb);

        return saveStudent;

    }

    public void delete(Long id){
        boolean existsById = studentRepository.existsById(id);

        if(!existsById){
            throw new NotFoundException(id);
        }else {
            studentRepository.deleteById(id);
        }
    }


    //Método redundante pora traer los ID
    public Student getStudentId(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    private void validateStudentAge(LocalDate dateOfBirth){
        int age = calculateAge(dateOfBirth);
        if (age < 16) {
            throw new BadRequestException("Student must be at least 16 years old.");
        }
        if (age > 200) {
            throw new BadRequestException("Student age cannot exceed 200 years.");
        }
    }

    private int calculateAge(LocalDate dateOfBirth){
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

}
