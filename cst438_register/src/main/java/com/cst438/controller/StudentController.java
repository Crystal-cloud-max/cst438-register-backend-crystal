package com.cst438.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Course;
import com.cst438.domain.Enrollment;
import com.cst438.domain.ScheduleDTO;
import com.cst438.domain.Student;
import com.cst438.domain.StudentDTO;
import com.cst438.domain.StudentRepository;

// RestController to receive HTTP requests and to perform JSON.
@RestController
@CrossOrigin
public class StudentController {

   // Autowired is used to enable dependency injection
   @Autowired
   StudentRepository studentRepository;
   
/* As an administrator, I can add a student to the system.  I input the student email and name.  
 * The student email must not already exists in the system.*/
   @PostMapping("student/create")
   @Transactional
   public StudentDTO addStudent(@RequestBody StudentDTO studentDTO) {
      // If the student already exists through the stored email.
      
      Student existingStudent = studentRepository.findByEmail(studentDTO.email);
      if (existingStudent == null) {
         // Add a new student and save it.
         Student newStudent = new Student();
         newStudent.setEmail(studentDTO.email);
         newStudent.setName(studentDTO.name);
         Student savedStudent = studentRepository.save(newStudent);
         
         StudentDTO result = createStudentDTO(savedStudent);
         return result;
      } else {
         throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student_id invalid or student not allowed to be added. "+studentDTO.student_id);
      }
   }
   
   private StudentDTO createStudentDTO(Student s) {
      StudentDTO studentDTO = new StudentDTO();
      studentDTO.student_id =s.getStudent_id();
      studentDTO.name = s.getName();
      studentDTO.email = s.getEmail();
      studentDTO.statusCode = s.getStatusCode();
      studentDTO.status = s.getStatus();
      
      return studentDTO;
   }

    // The Student object to change student information.
    @PostMapping("student/update")
    @Transactional
    public Student changeStudent(@RequestBody Student student) {
        // Find student in the data through the ID.
       Optional<Student> studentOptional = studentRepository.findById(student.getStudent_id());
    
       if (studentOptional.isPresent()) {
           // Update student status and save it.
          Student existingStudent = studentOptional.get();
          existingStudent.setStatusCode(student.getStatusCode());
          studentRepository.save(existingStudent);
          return existingStudent;
       }
        // Throw an error INTERNAL_SERVER_ERROR status if the student didn't found.
       throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Sorry, couldn't find student.");
    }

}
