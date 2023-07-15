package com.cst438.domain;

import org.springframework.data.repository.CrudRepository;

//CrudRepository is an interface for CRUD operations holds Student class and type ID field is int
public interface StudentRepository extends CrudRepository <Student, Integer> {
	
	// declare the following method to return a single Student object
	// default JPA behavior that findBy methods return List<Student> except for findById.
	public Student findByEmail(String email);

}
