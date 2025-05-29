package org.ironhack.coursequest.utility;


import org.ironhack.coursequest.repository.CourseRepository;
import org.ironhack.coursequest.repository.EnrollmentRepository;
import org.ironhack.coursequest.repository.InstitutionRepository;
import org.ironhack.coursequest.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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

    }



}
