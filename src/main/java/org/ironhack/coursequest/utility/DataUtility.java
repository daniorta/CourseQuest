package org.ironhack.coursequest.utility;


import org.ironhack.coursequest.model.Course;
import org.ironhack.coursequest.model.Institution;
import org.ironhack.coursequest.model.Student;
import org.ironhack.coursequest.repository.CourseRepository;
import org.ironhack.coursequest.repository.EnrollmentRepository;
import org.ironhack.coursequest.repository.InstitutionRepository;
import org.ironhack.coursequest.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataUtility implements CommandLineRunner {

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final InstitutionRepository institutionRepository;
    private final StudentRepository studentRepository;

    public DataUtility(CourseRepository courseRepository, EnrollmentRepository enrollmentRepository, InstitutionRepository institutionRepository, StudentRepository studentRepository){
        this.courseRepository = courseRepository;
        this.enrollmentRepository= enrollmentRepository;
        this.institutionRepository = institutionRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public void run(String...arg)throws Exception{

        //Creación de Cursos
        Course course1 = new Course();
        course1.setName("Desarrollo de Aplicaciones Web (DAW)");
        course1.setDuration(1600);
        course1.setTutor("Antonio Jurado");

        Course course2 = new Course();
        course2.setName("Desarrollo de Aplicaciones Multiplataforma (DAM)");
        course2.setDuration(2000);
        course2.setTutor("Vanesa Cortés");

        Course course3 = new Course();
        course3.setName("Administración de Sistemas Informáticos en Red (ASIR)");
        course3.setDuration(2501);
        course3.setTutor("José Sánchez");

        Course course4 = new Course();
        course4.setName("Ciberseguridad en Entornos de las Tecnologías de la Información (TIC)");
        course4.setDuration(1800);
        course4.setTutor("María Pérez");

        courseRepository.saveAll(List.of(course1, course2, course3, course4));

        //Creación de Institution
        Institution institution1 = new Institution(); // Desarrollo de Aplicaciones Web (DAM)
        institution1.setName("I.E.S Aguadulce");
        institution1.setAddress("Aguadulce, Sevilla. calle Alhambra, número 1.");
        institution1.setPhoneNumber(950156742);
        institution1.setEmail("04700260.secretaria@g.educaand.es");

        Institution institution2 = new Institution();
        institution2.setName("IES Francisco Tomás y Valiente"); // Administración de Sistemas Informáticos en Red (ASIR):
        institution2.setAddress("Calle de los Almendros, 1, 28250 Torrelodones, Madrid.");
        institution2.setPhoneNumber( 916406821);
        institution2.setEmail("asir@iesftomasvaliente.com");

        Institution institution3 = new Institution();
        institution3.setName("CIFP Carlos III"); //Ciberseguridad en Entornos de las Tecnologías de la Información (TIC)
        institution3.setAddress("Avenida de la Universidad, 14, 28911 Leganés, Madrid");
        institution3.setPhoneNumber(912001585);
        institution3.setEmail("ciberseguridad@cifpcarlos3.com");

        Institution institution4 = new Institution();
        institution4.setName("CIPFP Valle de Elda"); //Desarrollo de Aplicaciones Web (DAW
        institution4.setAddress("Calle de la Torreta de Elda, s/n, 03600 Elda, Alicante.");
        institution4.setPhoneNumber(965382745);
        institution4.setEmail("daw@cipfpvalleelda.com");

        institutionRepository.saveAll(List.of(institution1, institution2, institution3, institution4));


        //Creación de Student
        Student student1 = new Student();
        student1.setName("Alex");
        student1.setSurname("García Martínez");
        student1.setAddress("Calle Sol, 45, 28013 Madrid, España");
        student1.setDateOfBirth(LocalDate.of(1990, 3, 15));
        student1.setMail("alex.garcia@email.com");

        Student student2 = new Student();
        student2.setName("Carmen ");
        student2.setSurname("López Rodríguez");
        student2.setAddress("Avenida del Este, 22, 03008 Alicante, España");
        student2.setDateOfBirth(LocalDate.of(1985, 7, 22));
        student2.setMail("carmen.lopez@email.com");

        Student student3 = new Student();
        student3.setName("Jordan");
        student3.setSurname("Ruiz Fernández");
        student3.setAddress("Paseo del Prado, 34, 28014 Madrid, España");
        student3.setDateOfBirth(LocalDate.of(1992, 11, 30));
        student3.setMail("jordan.ruiz@email.com");

        Student student4 = new Student();
        student4.setName("María ");
        student4.setSurname("Sánchez Torres");
        student4.setAddress("Carrer de la Mar, 5, 46003 Valencia, España");
        student4.setDateOfBirth(LocalDate.of(1988, 5, 16));
        student4.setMail("maria.sanchez@email.com");

        Student student5 = new Student();
        student5.setName("Luis");
        student5.setSurname("Castillo Gómez");
        student5.setAddress("Gran Vía, 18, 28013 Madrid, España");
        student5.setDateOfBirth(LocalDate.of(1995, 2, 25));
        student5.setMail("luis.castillo@email.com");

        Student student6 = new Student();
        student6.setName("Andrea");
        student6.setSurname("Navarro Díaz");
        student6.setAddress("Calle Luna, 60, 29010 Málaga, España");
        student6.setDateOfBirth(LocalDate.of(2000, 8, 10));
        student6.setMail("andrea.navarro@email.com");

        studentRepository.saveAll(List.of(student1, student2, student3, student4, student5, student6));

        //Creación de Enrollment
        //Tenemos que asociar el studiante y el curso

    }



}
