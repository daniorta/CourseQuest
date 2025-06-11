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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        //Falta lanzar un error en postman.
        return enrollmentRepository.findAll();
    }


    public Enrollment getById(Long id){
        Enrollment enrollment = getEnrollment(id); // Obtiene la matrícula de la base de datos
        checkOwnerPermissions(enrollment);
        return getEnrollment(id);
    }

    public Enrollment createEnrollment(EnrollmentDTO enrollmentDTO){

        // Obtén el nombre de usuario autenticado
        String currentUsername = getAuthenticatedUsername();

        //Relacionar la matricula con el curso y con el studiante.
        Student student = studentRepository.findById(enrollmentDTO.getStudentId())
                .orElseThrow(() -> new BadRequestException("Student ID " + enrollmentDTO.getStudentId() + " not found."));


        // Verifica que el estudiante corresponda al usuario autenticado
        if (!student.getUsername().equals(currentUsername)) {
            throw new SecurityException("User is not allowed to create enrollment for another student.");
        }

        Course course = courseRepository.findById(enrollmentDTO.getCourseId())
                .orElseThrow(() -> new BadRequestException("Course ID " + enrollmentDTO.getCourseId() + " not found."));

        //Verificamos si la matricula existe.
        if (enrollmentExists(student.getId(), course.getId())) {
            throw new BadRequestException("Enrollment already exists for this student and course.");
        }

        Enrollment newEnrollment = new Enrollment();
        newEnrollment.setEnrollmentDate(enrollmentDTO.getEnrollmentDate());
        newEnrollment.setStatus(enrollmentDTO.getStatus());
//        newEnrollment.setGrade(enrollmentDTO.getGrade());
        newEnrollment.setEnding(enrollmentDTO.getEnding());
        newEnrollment.setCourse(course);
        newEnrollment.setStudent(student);

        validateEnrollmentDTO(enrollmentDTO);
        Enrollment saveEnrollment = enrollmentRepository.save(newEnrollment);

        return saveEnrollment;
    }

    public Enrollment updateEnrollment(Long id, EnrollmentDTO enrollmentDTO){

        Enrollment enrollmentFromDb = getEnrollment(id);

        checkOwnerPermissions(enrollmentFromDb);

        enrollmentFromDb.setEnrollmentDate(enrollmentDTO.getEnrollmentDate());
        enrollmentFromDb.setStatus(enrollmentDTO.getStatus());
//      enrollmentFromDb.setGrade(enrollmentDTO.getGrade());
        enrollmentFromDb.setEnding(enrollmentDTO.getEnding());

        Course course = courseRepository.findById(enrollmentDTO.getCourseId())
                .orElseThrow(() -> new BadRequestException("Course ID " + enrollmentDTO.getCourseId() + " not found."));
        enrollmentFromDb.setCourse(course);

        validateEnrollmentDTO(enrollmentDTO);
        Enrollment saveEnrollment = enrollmentRepository.save(enrollmentFromDb);

        return saveEnrollment;
    }


    public Enrollment updateGradeEnrollment(Long id, EnrollmentGradeDTO enrollmentGradeDTO) {
        Enrollment enrollmentFromDb = getEnrollment(id);

        if (!hasRoleAdmin()) {
            throw new SecurityException("Only admins can modify grades.");
        }

        enrollmentFromDb.setGrade(enrollmentGradeDTO.getGrade());
        return enrollmentRepository.save(enrollmentFromDb);
    }


    public void deleteEnrollment(Long id) {
        Enrollment enrollment = getEnrollment(id);  // Obtiene la inscripción de la base de datos
        checkOwnerPermissions(enrollment);  // Verifica que el usuario tenga permisos
        enrollmentRepository.deleteById(id);  // Elimina la inscripción
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

    //metodo que utilizamos para ver si el usuario tiene una matricula, no pueda crear otra en el mismo curso
    private boolean enrollmentExists(Long studentId, Long courseId) {
        return enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId).isPresent();
    }


    //Métodos para redundancia de busqueda por id.
    public Enrollment getEnrollment(Long id){
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    //Validacion para las fechas de inicio y final
    private void validateEnrollmentDTO(EnrollmentDTO enrollmentDTO) {

        if (enrollmentDTO.getEnrollmentDate().isAfter(LocalDate.now())){
            throw new BadRequestException("Enrollment date cannot be in the future.");
        }
        if(enrollmentDTO.getEnding().isAfter(LocalDate.now())){
            throw new BadRequestException("Ending date cannot be in the future.");
        }
    }

    //Método para opbtener el nombre del usuario
    private String getAuthenticatedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new SecurityException("No user is currently authenticated");
        }
        return authentication.getName();
    }

    //Método para verificar que el student que intenta eliminar o actualizar corresponda con la matricula que es de el
    private void checkOwnerPermissions(Enrollment enrollment) {
        String currentUsername = getAuthenticatedUsername();

        // Permite a los administradores realizar cualquier operación
        if (hasRoleAdmin()) {
            return; // Si es admin, no hay más verificaciones requeridas
        }

        // Verifica si el usuario actual es el dueño de la matrícula o un administrador
        if (!enrollment.getStudent().getUsername().equals(currentUsername)) {
            throw new SecurityException("User is not allowed to modify or delete this enrollment.");
        }
    }

    private boolean hasRoleAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
    }



}




