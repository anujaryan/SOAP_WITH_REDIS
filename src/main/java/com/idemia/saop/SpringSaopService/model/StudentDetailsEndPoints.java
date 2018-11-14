package com.idemia.saop.SpringSaopService.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.idemia.saop.SpringSaopService.service.StudentService;

import localhost._8080.students.AddStudentRequest;
import localhost._8080.students.AddStudentResponse;
import localhost._8080.students.DeleteStudentRequest;
import localhost._8080.students.DeleteStudentResponse;
import localhost._8080.students.GetAllStudentRequest;
import localhost._8080.students.GetAllStudentsResponse;
import localhost._8080.students.GetStudentDetailsRequest;
import localhost._8080.students.GetStudentDetailsResponse;
import localhost._8080.students.ServiceStatus;
import localhost._8080.students.StudentByPassNumRequest;
import localhost._8080.students.StudentByPassNumResponse;
import localhost._8080.students.StudentDetails;
import localhost._8080.students.UpdateStudentRequest;
import localhost._8080.students.UpdateStudentResponse;

@Endpoint
public class StudentDetailsEndPoints {
	
	private static final String NAMESPACE_URI = "http://localhost:8080/students";
	

	public  StudentService studentService;
	
	public StudentDetailsEndPoints(StudentService studentService) {
		this.studentService=studentService;
	}
	
	

	  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetStudentDetailsRequest")
	  @ResponsePayload
	  public GetStudentDetailsResponse processCourseDetailsRequest(@RequestPayload GetStudentDetailsRequest request) {
	    GetStudentDetailsResponse response = new GetStudentDetailsResponse();
	    
	    StudentDetails studentDetails = new StudentDetails();
	    studentDetails.setId(request.getId());
	    studentDetails.setName("Adam");
	    studentDetails.setPassportNumber("E1234567");
	    
	    response.setStudentDetails(studentDetails);
	    
	    return response;
	  }
	  
	  
	  
	  @PayloadRoot(namespace=NAMESPACE_URI,localPart="getAllStudentRequest")
	  @ResponsePayload
	  public GetAllStudentsResponse processAllStudentDetails(@RequestPayload GetAllStudentRequest request) {
		  GetAllStudentsResponse response=new GetAllStudentsResponse();
		  
		  List<StudentDetails> studentinfo=new ArrayList<StudentDetails>();
		  
		  LinkedHashMap<Object, Object>  list=studentService.studentAll();
		  
		    
		  Set<Entry<Object, Object>> itr=list.entrySet();
		  
		  
		 for(Entry<Object, Object> entry:list.entrySet()) {
			Student strobject = (Student) entry.getValue();
			StudentDetails studinfo = new StudentDetails();
			BeanUtils.copyProperties(strobject, studinfo);
			studentinfo.add(studinfo);
		 }
		
		   response.getStudentDetails().addAll(studentinfo);
		  return response;
		  
	  }
	  
	  
	  @PayloadRoot(namespace=NAMESPACE_URI,localPart="addStudentRequest")
	  @ResponsePayload
	  
	  public AddStudentResponse processAddStudentRequest(@RequestPayload AddStudentRequest request)
	  {
		AddStudentResponse response = new AddStudentResponse();
		ServiceStatus servicestatus = new ServiceStatus();

		Student student = new Student();
		student.setId(request.getId());
		student.setName(request.getName());
		student.setPassportNumber(request.getPassportNumber());

		boolean flag = studentService.saveStudent(student);

		if (flag == false) {

			servicestatus.setStatusCode("CONFLICT");
			servicestatus.setMessage("Content already exist");
			response.setServiceStatus(servicestatus);

		} else {
			StudentDetails stdtls = new StudentDetails();
			BeanUtils.copyProperties(student, stdtls);
			response.setStudentDetails(stdtls);
			servicestatus.setStatusCode("SUCCESS");
			servicestatus.setMessage("Content added successfully");
			response.setServiceStatus(servicestatus);
		}

		return response;
		  
	  }
	  
	  @PayloadRoot(namespace=NAMESPACE_URI,localPart="deleteStudentRequest")
	  @ResponsePayload
	  public DeleteStudentResponse processStudentDelete(@RequestPayload DeleteStudentRequest request) {
		
		  
		Student student = studentService.studentbyPassNumber(request.getPassportNumber());

		ServiceStatus servicestatus = new ServiceStatus();

		if (student == null) {
			servicestatus.setStatusCode("FAIL");
			servicestatus.setMessage("Content Not Available");
		} else {
			studentService.deleteStudent(request.getPassportNumber());
			servicestatus.setStatusCode("SUCCESS");
			servicestatus.setMessage("Content Deleted Successfully");
		}
		DeleteStudentResponse response = new DeleteStudentResponse();
		response.setServiceStatus(servicestatus);
		return response;

	  }
	  @PayloadRoot(namespace=NAMESPACE_URI,localPart="StudentByPassNumRequest")
	  @ResponsePayload
	  public StudentByPassNumResponse processStudentByPassNum(@RequestPayload StudentByPassNumRequest request)
	  {
		  
		  StudentByPassNumResponse response=new StudentByPassNumResponse();
		 StudentDetails studentdetails=new StudentDetails();
		  
		  
		  Student student=studentService.studentbyPassNumber(request.getPassportNumber());
		  BeanUtils.copyProperties(student, studentdetails);
		  
		  
		  response.setStudentDetails(studentdetails);
		   
		return response;
		  
		  
	  }
	 
	  
	  @PayloadRoot(namespace=NAMESPACE_URI,localPart="updateStudentRequest")
	  @ResponsePayload
	  public UpdateStudentResponse processStudentRequest(@RequestPayload UpdateStudentRequest request)
	  {
		  Student student=new Student();
		  
		  BeanUtils.copyProperties(request.getStudentDetails(), student);
		  
		  studentService.updateStudentById(student);
		  
		  ServiceStatus servicestatus=new ServiceStatus();
		  
		  servicestatus.setStatusCode("SUCCESS");
		  servicestatus.setMessage("Content updated Successfully");
		  UpdateStudentResponse response=new UpdateStudentResponse();
		  response.setServiceStatus(servicestatus);
		 return response;
		  
	  }
	  
	  
}
