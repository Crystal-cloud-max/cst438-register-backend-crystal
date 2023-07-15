package com.cst438.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cst438.domain.CourseDTOG;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentRepository;

//RestController is a java class that receives HTTP requests, it works with data in JSON format
@RestController
@CrossOrigin
public class CourseController {
   //Autowired is used to enable dependency injection
	@Autowired
	EnrollmentRepository enrollmentRepository;
	
	/*
	 * endpoint used by gradebook service to transfer final course grades
	 * used to update path /course/id
	 */
	@PutMapping("/course/{course_id}")
	@Transactional
	public void updateCourseGrades( @RequestBody CourseDTOG courseDTO, @PathVariable("course_id") int course_id) {
		
		//TODO  complete this method in homework 4
	   //loop through all course grades
	   for(CourseDTOG.GradeDTO g : courseDTO.grades) {
	      //for each grade I want to find enrollment record for that student by email and id
	      Enrollment e = enrollmentRepository.findByEmailAndCourseId(g.student_email, course_id);
	      
	      //set the grade for the course
	      e.setCourseGrade(g.grade);
	      
	      //save it back to the database
	      enrollmentRepository.save(e);
	   }
		
	}

}
