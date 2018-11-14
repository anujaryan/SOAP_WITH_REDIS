/**
 * 
 */
package com.idemia.saop.SpringSaopService.service;

import java.util.LinkedHashMap;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.idemia.saop.SpringSaopService.model.Student;

/**
 * @author G521980
 *
 */
@Service
public class StudentServiceImpl implements StudentService {
	
	
	private static final String REDIX_INDEX_KEY="STUDENT";
	
	private RedisTemplate<String, Student> redistemplate;
	
	private HashOperations hashoperation;
	
	public StudentServiceImpl(RedisTemplate<String, Student> redistemplate) {
		this.redistemplate=redistemplate;
		this.hashoperation=redistemplate.opsForHash();
	}
	
	

	/* (non-Javadoc)
	 * @see com.idemia.saop.SpringSaopService.service.StudentService#studentbyId(java.lang.Long)
	 */
	@Override
	public Student studentbyPassNumber(String passnum) {
		// TODO Auto-generated method stub
		
		System.out.println("Method is calling here...");
     Student std=(Student) hashoperation.get(REDIX_INDEX_KEY, passnum);
     System.out.println("Method is calling  end here...");
	return std;
		
	}

	/* (non-Javadoc)
	 * @see com.idemia.saop.SpringSaopService.service.StudentService#studentAll()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap<Object, Object> studentAll() {
		// TODO Auto-generated method stub
		
		
		LinkedHashMap<Object, Object> list=(LinkedHashMap<Object, Object>) hashoperation.entries(REDIX_INDEX_KEY);
		return list;
	}

	/* (non-Javadoc)
	 * @see com.idemia.saop.SpringSaopService.service.StudentService#updateStudentById(com.idemia.saop.SpringSaopService.model.Student)
	 */
	@Override
	public void updateStudentById(Student student) {
		// TODO Auto-generated method stub
		saveStudent(student);
	}

	/* (non-Javadoc)
	 * @see com.idemia.saop.SpringSaopService.service.StudentService#deleteStudent(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void deleteStudent(String passnum) {
		// TODO Auto-generated method stub
		
		hashoperation.delete(REDIX_INDEX_KEY, passnum);

	}



	@SuppressWarnings("unchecked")
	@Override
	public boolean saveStudent(Student student) {
		// TODO Auto-generated method stub
		
		hashoperation.put(REDIX_INDEX_KEY, student.getPassportNumber(), student);
		
		//Student std=(Student) hashoperation.get(REDIX_INDEX_KEY, student.getPassportNumber());
		
		
		return true;
	}

}
