package org.ironhack.coursequest.service;

import org.ironhack.coursequest.dto.StudentDTO;
import org.ironhack.coursequest.exception.BadRequestException;
import org.ironhack.coursequest.exception.NotFoundException;
import org.ironhack.coursequest.model.Student;
import org.ironhack.coursequest.repository.StudentRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudent(){
        return studentRepository.findAll();
    }

    public Student getById(Long id) {
        Student student = getStudentId(id);
        checkOwnerPermissions(student);
        return student;
    }

    public Student createStudent(StudentDTO studentDTO){
        // Obtener el correo electrónico del usuario autenticado
        String authenticatedName = getAuthenticatedUsername();

        // Verificar si ya existe un estudiante con el nombre del usuario autenticado
        if (nameExists(authenticatedName)) {
            throw new BadRequestException("A student profile already exists for this name. Please delete the existing profile before attempting to create a new one.");
        }

        Student newStudent = new Student();
        newStudent.setName(studentDTO.getName());
        newStudent.setSurname(studentDTO.getSurname());
        newStudent.setUsername(studentDTO.getUsername());
        newStudent.setAddress(studentDTO.getAddress());
        newStudent.setDateOfBirth(studentDTO.getDateOfBirth());
        newStudent.setEmail(studentDTO.getMail());

        validateStudentAge(studentDTO.getDateOfBirth());
        Student saveStudent = studentRepository.save(newStudent);

        return saveStudent;
    }



    public Student updateStudent(Long id, StudentDTO studentDTO){

        Student studentFromDb = getStudentId(id);

        checkOwnerPermissions(studentFromDb);

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
        // Verifica si el estudiante existe
        Student studentFromDb = getStudentId(id);

        // Verifica los permisos del usuario autenticado para este estudiante
        checkOwnerPermissions(studentFromDb);

        // Si las verificaciones pasan, procede a eliminar
        studentRepository.deleteById(id);
    }


    //Método redundante pora traer los ID
    public Student getStudentId(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    //Método para ver si el email existe a la hora de crear un nuevo student
    private boolean nameExists(String name) {
        return studentRepository.findByName(name).isPresent();
    }

    //Método para calcular edad.
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
    //----
    //Método para opbtener el nombre del usuario
    private String getAuthenticatedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new SecurityException("No user is currently authenticated");
        }
        return authentication.getName();
    }

    //Método para verificar que el student que intenta eliminar o actualizar corresponda con la matricula que es de el
    private void checkOwnerPermissions(Student student) {
        String currentUsername = getAuthenticatedUsername();

        // Permite a los administradores realizar cualquier operación
        if (hasRoleAdmin()) {
            return; // Si es admin, no hay más verificaciones requeridas
        }

        // Verifica si el usuario actual es el dueño de la matrícula o un administrador
        if (!student.getUsername().equals(currentUsername)) {
            throw new SecurityException("Action not permitted by this user.");
        }
    }

    private boolean hasRoleAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
    }
}
