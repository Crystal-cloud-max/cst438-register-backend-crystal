package com.cst438.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Student;
import com.cst438.domain.StudentRepository;

// RestController to receive HTTP requests and to perform JSON.
@RestController
public class StudentController {

   // Autowired is used to enable dependency injection
   @Autowired
   StudentRepository studentRepository;
/* As an administrator, I can add a student to the system.  I input the student email and name.  
 * The student email must not already exists in the system.*/

   @PostMapping("student/create")
   @Transactional
   public void addStudent(@RequestBody Student student) {
      // If the student already exists through the stored email.
      Student existingStudent = studentRepository.findByEmail(student.getEmail());
      if (existingStudent == null) {
         // Add a new student and save it.
         Student newStudent = new Student();
         newStudent.setEmail(student.getEmail());
         newStudent.setName(student.getName());
         studentRepository.save(newStudent);
      }
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
