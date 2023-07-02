package com.cst438.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.cst438.domain.StudentRepository;

//RestController is a java class that receives HTTP requests, it works with data in JSON format
@RestController
public class StudentController{
   //Autowired is used to enable dependency injection
   @Autowired
   StudentRepository studentRepository;
   
}
