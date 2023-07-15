package com.cst438.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

//CrudRepository is an interface for CRUD operations holds Enrollment class and type ID is int
public interface EnrollmentRepository extends CrudRepository <Enrollment, Integer> {
   
	 //find student's schedule by email,year,and semester
	@Query("select e from Enrollment e where e.student.email=:email and e.year=:year and e.semester=:semester")
	public List<Enrollment> findStudentSchedule(
			@Param("email") String email, 
			@Param("year") int year, 
			@Param("semester") String semester);
	
	//find student by email and course id
	@Query("select e from Enrollment e where e.student.email=:email and e.course.course_id=:course_id")
	Enrollment findByEmailAndCourseId(@Param("email") String email, @Param("course_id") int course_id);
	
	@SuppressWarnings("unchecked")
	Enrollment save(Enrollment e);	
}