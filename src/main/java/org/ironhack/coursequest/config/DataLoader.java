package org.ironhack.coursequest.config;

import lombok.RequiredArgsConstructor;
import org.ironhack.coursequest.enuns.Status;
import org.ironhack.coursequest.model.*;
import org.ironhack.coursequest.repository.CourseRepository;
import org.ironhack.coursequest.repository.EnrollmentRepository;
import org.ironhack.coursequest.repository.InstitutionRepository;
import org.ironhack.coursequest.repository.StudentRepository;
import org.ironhack.coursequest.service.RoleService;
import org.ironhack.coursequest.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserService userService;
    private final RoleService roleService;

    //-----

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final InstitutionRepository institutionRepository;
    private final StudentRepository studentRepository;


    @Override
    public void run(String... args) throws Exception {
        roleService.save(new Role("ROLE_USER"));
        roleService.save(new Role("ROLE_ADMIN"));



        //Creación de Cursos
        Course course1 = new Course();
        course1.setName("Desarrollo de Aplicaciones Web (DAW)");
        course1.setDuration(1600);
        course1.setCapacity(14);
        course1.setTutor("Antonio Jurado");

        Course course2 = new Course();
        course2.setName("Desarrollo de Aplicaciones Multiplataforma (DAM)");
        course2.setDuration(2000);
        course2.setCapacity(10);
        course2.setTutor("Vanesa Cortés");

        Course course3 = new Course();
        course3.setName("Administración de Sistemas Informáticos en Red (ASIR)");
        course3.setDuration(2501);
        course3.setCapacity(15);
        course3.setTutor("José Sánchez");

        Course course4 = new Course();
        course4.setName("Ciberseguridad en Entornos de las Tecnologías de la Información (TIC)");
        course4.setDuration(1800);
        course4.setCapacity(9);
        course4.setTutor("María Pérez");

        courseRepository.saveAll(List.of(course1, course2, course3, course4));

        //Creación de Institution
        Institution institution1 = new Institution(); // Desarrollo de Aplicaciones Web (DAM)
        institution1.setName("I.E.S Aguadulce");
        institution1.setAddress("Aguadulce, Sevilla. calle Alhambra, número 1.");
        institution1.setPhoneNumber(950156742);
        institution1.setEmail("04700260.secretaria@g.educaand.es");
        institution1.setUsername("aguadulce");
        institution1.setPassword("1234");

        Institution institution2 = new Institution();
        institution2.setName("IES Francisco Tomás y Valiente"); // Administración de Sistemas Informáticos en Red (ASIR):
        institution2.setAddress("Calle de los Almendros, 1, 28250 Torrelodones, Madrid.");
        institution2.setPhoneNumber( 916406821);
        institution2.setEmail("asir@iesftomasvaliente.com");
        institution2.setUsername("valiente");
        institution2.setPassword("1234");

        Institution institution3 = new Institution();
        institution3.setName("CIFP Carlos III"); //Ciberseguridad en Entornos de las Tecnologías de la Información (TIC)
        institution3.setAddress("Avenida de la Universidad, 14, 28911 Leganés, Madrid");
        institution3.setPhoneNumber(912001585);
        institution3.setEmail("ciberseguridad@cifpcarlos3.com");
        institution3.setUsername("carlos3");
        institution3.setPassword("1234");

        Institution institution4 = new Institution();
        institution4.setName("CIPFP Valle de Elda"); //Desarrollo de Aplicaciones Web (DAW
        institution4.setAddress("Calle de la Torreta de Elda, s/n, 03600 Elda, Alicante.");
        institution4.setPhoneNumber(965382745);
        institution4.setEmail("daw@cipfpvalleelda.com");
        institution4.setUsername("elda");
        institution4.setPassword("1234");

        userService.saveUser(institution1);
        userService.saveUser(institution2);
        userService.saveUser(institution3);
        userService.saveUser(institution4);

        roleService.addRoleToUser("aguadulce", "ROLE_ADMIN");
        roleService.addRoleToUser("valiente", "ROLE_ADMIN");
        roleService.addRoleToUser("carlos3", "ROLE_ADMIN");
        roleService.addRoleToUser("elda", "ROLE_ADMIN");
       // institutionRepository.saveAll(List.of(institution1, institution2, institution3, institution4));


        //Creación de Student
        Student student1 = new Student();
        student1.setName("Alex");
        student1.setSurname("García Martínez");
        student1.setAddress("Calle Sol, 45, 28013 Madrid, España");
        student1.setDateOfBirth(LocalDate.of(1990, 3, 15));
        student1.setEmail("alex.garcia@email.com");
        student1.setUsername("alex");
        student1.setPassword("1234");

        Student student2 = new Student();
        student2.setName("Carmen ");
        student2.setSurname("López Rodríguez");
        student2.setAddress("Avenida del Este, 22, 03008 Alicante, España");
        student2.setDateOfBirth(LocalDate.of(1985, 7, 22));
        student2.setEmail("carmen.lopez@email.com");
        student2.setUsername("carmen");
        student2.setPassword("1234");

        Student student3 = new Student();
        student3.setName("Jordan");
        student3.setSurname("Ruiz Fernández");
        student3.setAddress("Paseo del Prado, 34, 28014 Madrid, España");
        student3.setDateOfBirth(LocalDate.of(1992, 11, 30));
        student3.setEmail("jordan.ruiz@email.com");
        student3.setUsername("jordan");
        student3.setPassword("1234");

        Student student4 = new Student();
        student4.setName("María ");
        student4.setSurname("Sánchez Torres");
        student4.setAddress("Carrer de la Mar, 5, 46003 Valencia, España");
        student4.setDateOfBirth(LocalDate.of(1988, 5, 16));
        student4.setEmail("maria.sanchez@email.com");
        student4.setUsername("maria");
        student4.setPassword("1234");

        Student student5 = new Student();
        student5.setName("Luis");
        student5.setSurname("Castillo Gómez");
        student5.setAddress("Gran Vía, 18, 28013 Madrid, España");
        student5.setDateOfBirth(LocalDate.of(1995, 2, 25));
        student5.setEmail("luis.castillo@email.com");
        student5.setUsername("luis");
        student5.setPassword("1234");

        Student student6 = new Student();
        student6.setName("Andrea");
        student6.setSurname("Navarro Díaz");
        student6.setAddress("Calle Luna, 60, 29010 Málaga, España");
        student6.setDateOfBirth(LocalDate.of(2000, 8, 10));
        student6.setEmail("andrea.navarro@email.com");
        student6.setUsername("andrea");
        student6.setPassword("1234");

        userService.saveUser(student1);
        userService.saveUser(student2);
        userService.saveUser(student3);
        userService.saveUser(student4);
        userService.saveUser(student5);
        userService.saveUser(student6);

        roleService.addRoleToUser("alex", "ROLE_USER");
        roleService.addRoleToUser("carmen", "ROLE_USER");
        roleService.addRoleToUser("jordan", "ROLE_USER");
        roleService.addRoleToUser("maria", "ROLE_USER");
        roleService.addRoleToUser("luis", "ROLE_USER");
        roleService.addRoleToUser("andrea", "ROLE_USER");


       // studentRepository.saveAll(List.of(student1, student2, student3, student4, student5, student6));

        //Creación de Enrollment
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setEnrollmentDate(LocalDate.of(2023, 1, 15));
        enrollment1.setStatus(Status.ACTIVE);
        enrollment1.setGrade(85.5);
        enrollment1.setEnding(LocalDate.of(2023, 12, 15));
        enrollment1.setStudent(student1);
        enrollment1.setCourse(course1);

        Enrollment enrollment2 = new Enrollment();
        enrollment2.setEnrollmentDate(LocalDate.of(2020, 2, 10));
        enrollment2.setStatus(Status.CANCEL);
        enrollment2.setGrade(76.1);
        enrollment2.setEnding(LocalDate.of(2023, 11, 10));
        enrollment2.setStudent(student2);
        enrollment2.setCourse(course1);

        Enrollment enrollment3 = new Enrollment();
        enrollment3.setEnrollmentDate(LocalDate.of(2023, 3, 5));
        enrollment3.setStatus(Status.COMPLETE);
        enrollment3.setGrade(55.9);
        enrollment3.setEnding(LocalDate.of(2023, 10, 5));
        enrollment3.setStudent(student3);
        enrollment3.setCourse(course2);

        Enrollment enrollment4 = new Enrollment();
        enrollment4.setEnrollmentDate(LocalDate.of(2023, 4, 20));
        enrollment4.setStatus(Status.INCOMPLETE);
        enrollment4.setGrade(85.5);
        enrollment4.setEnding(LocalDate.of(2024, 3, 20));
        enrollment4.setStudent(student4);
        enrollment4.setCourse(course3);

        Enrollment enrollment5 = new Enrollment();
        enrollment5.setEnrollmentDate(LocalDate.of(2020, 5, 30));
        enrollment5.setStatus(Status.COMPLETE);
        enrollment5.setGrade(85.5);
        enrollment5.setEnding(LocalDate.of(2020,  2, 26));
        enrollment5.setStudent(student5);
        enrollment5.setCourse(course4);

        Enrollment enrollment6 = new Enrollment();
        enrollment6.setEnrollmentDate(LocalDate.of(2023,  12, 30));
        enrollment6.setStatus(Status.ACTIVE);
        enrollment6.setGrade(85.5);
        enrollment6.setEnding(LocalDate.of(2023, 12, 15));
        enrollment6.setStudent(student6);
        enrollment6.setCourse(course3);

        enrollmentRepository.saveAll(List.of(enrollment1, enrollment2, enrollment3, enrollment4, enrollment5, enrollment6));

        //Añadir la matricula despues de guardarla.
        student1.getEnrollmentList().add(enrollment1);
        student2.getEnrollmentList().add(enrollment2);
        student3.getEnrollmentList().add(enrollment3);
        student4.getEnrollmentList().add(enrollment4);
        student5.getEnrollmentList().add(enrollment5);
        student6.getEnrollmentList().add(enrollment6);


        //Añadimos las matriculas al curso despues de guardar
        course1.getEnrollmentList().addAll(List.of(enrollment1, enrollment2));
        course2.getEnrollmentList().add(enrollment3);
        course3.getEnrollmentList().addAll(List.of(enrollment4, enrollment6));
        course4.getEnrollmentList().add(enrollment5);

    }
}