package com.cst438.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import com.cst438.domain.EnrollmentDTO;


public class GradebookServiceREST extends GradebookService {

	private RestTemplate restTemplate = new RestTemplate();

	@Value("${gradebook.url}")
	String gradebook_url;
	
	//displays in console window when registration.service = REST in application.properties
	public GradebookServiceREST() {
		System.out.println("REST grade book service");
	}

	@Override
	public void enrollStudent(String student_email, String student_name, int course_id) {
		
		//TODO  complete this method in homework 4
	   
	   //EnrollmentDTO is from gradebook/EnrollmentController.java for addEnrollment function path /enrollment
	   EnrollmentDTO enrollment = new EnrollmentDTO();
	   enrollment.course_id = course_id;
	   enrollment.studentEmail = student_email;
	   enrollment.studentName = student_name;
	   
	   System.out.println("Post to gradebook " + enrollment);//for debugging purpose
	   
	   //create a response: url comes from application.properties, enrollment is object and class is returned
	   //response is doing HTTP POST call to the GradebookService with path name
	   EnrollmentDTO response = restTemplate.postForObject(gradebook_url + "/enrollment", enrollment, EnrollmentDTO.class);
	   System.out.println("Response from gradebook " + response);
		
	}
}
