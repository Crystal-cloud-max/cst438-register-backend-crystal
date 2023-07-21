package com.cst438.service;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.cst438.domain.CourseDTOG;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentDTO;
import com.cst438.domain.EnrollmentRepository;

/*this class both sends msg for new enrollment and receives msg of final grades
 * to from the gradebook backend*/
public class GradebookServiceMQ extends GradebookService {
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Autowired
	EnrollmentRepository enrollmentRepository;
	
	@Autowired
	Queue gradebookQueue;
	
	
	public GradebookServiceMQ() {
		System.out.println("MQ grade book service");
	}
	
	// send message to grade book service about new student enrollment in course
	@Override
	public void enrollStudent(String student_email, String student_name, int course_id) {
		 
		// create EnrollmentDTO and send to gradebookQueue
		EnrollmentDTO enrollmentDTO = new EnrollmentDTO(student_email, student_name, course_id);
		rabbitTemplate.convertAndSend(gradebookQueue.getName(), enrollmentDTO);
		System.out.println("Message send to gradbook service for student "+ student_email +" " + course_id);  
		
	}
	
	@RabbitListener(queues = "registration-queue")
	public void receive(CourseDTOG courseDTOG) {
		System.out.println("Receive enrollment :" + courseDTOG);
 
		// for each student grade in courseDTOG,  find the student enrollment entity, update the grade and save back to enrollmentRepository.
		
		//process the list of student grades
		for(CourseDTOG.GradeDTO g : courseDTOG.grades) {
         //for each grade I want to find enrollment record for that student by email and id
         Enrollment e = enrollmentRepository.findByEmailAndCourseId(g.student_email, courseDTOG.course_id);
         //set the grade
         e.setCourseGrade(g.grade);
         //save it back to the database
         enrollmentRepository.save(e);
         System.out.println("final grade output " + g.student_email + " " + courseDTOG.course_id + " " + g.grade);
		}
	}

}
