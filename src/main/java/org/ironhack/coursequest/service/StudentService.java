package org.ironhack.coursequest.service;

import org.ironhack.coursequest.dto.StudentDTO;
import org.ironhack.coursequest.model.Student;
import org.ironhack.coursequest.repository.StudentRepository;
import org.springframework.stereotype.Service;

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
        Optional<Student> optionalStudent = getStudentId(id);
        return optionalStudent.get();
    }

    public Student createStudent(StudentDTO studentDTO){
        Student newStudent = new Student();

        newStudent.setName(studentDTO.getName());
        newStudent.setSurname(studentDTO.getSurname());
        newStudent.setAddress(studentDTO.getAddress());
        newStudent.setDateOfBirth(studentDTO.getDateOfBirth());
        newStudent.setMail(studentDTO.getMail());

        Student saveStudent = studentRepository.save(newStudent);

        return saveStudent;


    }


    public Student updateStudent(Long id, StudentDTO studentDTO){
        Optional<Student> optionalStudent = getStudentId(id);

        if(optionalStudent.isEmpty()){
            System.out.println("Id Student not found.");
            return null;
        }

        Student studentFromDb = optionalStudent.get();

        studentFromDb.setName(studentDTO.getName());
        studentFromDb.setSurname(studentDTO.getSurname());
        studentFromDb.setAddress(studentDTO.getAddress());
        studentFromDb.setDateOfBirth(studentDTO.getDateOfBirth());
        studentFromDb.setMail(studentDTO.getMail());

        Student saveStudent = studentRepository.save(studentFromDb);

        return saveStudent;

    }

    public void delete(Long id){
        boolean existsById = studentRepository.existsById(id);

        if(!existsById){
            System.out.println("Id Student " + id + "not found");
        }else {
            studentRepository.deleteById(id);
        }


    }



    //Método redundante pora traer los ID
    public Optional<Student> getStudentId(Long id){
        Optional<Student> optionalStudent = studentRepository.findById(id);

        if(optionalStudent.isEmpty()){
            System.out.println("Id not found");
        }
        return optionalStudent;
    }

}
