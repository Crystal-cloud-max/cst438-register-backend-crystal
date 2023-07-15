package com.cst438.domain;


import org.springframework.data.repository.CrudRepository;

//CrudRepository is an interface for CRUD operations holds Course class and type ID is int
public interface CourseRepository extends CrudRepository <Course, Integer> {
	
}