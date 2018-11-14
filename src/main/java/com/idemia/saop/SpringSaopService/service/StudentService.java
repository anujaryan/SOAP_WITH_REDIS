package com.idemia.saop.SpringSaopService.service;

import java.util.LinkedHashMap;
import java.util.List;

import com.idemia.saop.SpringSaopService.model.Student;

public interface StudentService {

	
	public boolean saveStudent(Student student);
	public Student studentbyPassNumber(String passnum);

	public LinkedHashMap<Object, Object> studentAll();

	public void updateStudentById(Student Student);

	public void deleteStudent(String passnum);
	

}
