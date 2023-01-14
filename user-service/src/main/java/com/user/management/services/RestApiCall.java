package com.user.management.services;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.user.management.payload.request.SignupRequest;
import com.user.management.payload.request.UpdateUserDetails;
import com.user.management.payload.response.MessageResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Component
public class RestApiCall {
//    private static final String BOOK_URL = "http://localhost:8082/books/";
    private static final String EMPLOYEE_URL = "http://localhost:8082/api/employee/manage";

    @Autowired
    RestTemplate restTemplate;

    
    public ResponseEntity<String> saveEmployee(String url, SignupRequest request) {

        String result = restTemplate.postForObject(EMPLOYEE_URL+url, request, String.class);
        assert result != null;
        return ResponseEntity.ok(result);
    }
    
    


	public ResponseEntity<String> updateEmployee(String url, UpdateUserDetails userDetails) {
		String result = restTemplate.postForObject(EMPLOYEE_URL+url, userDetails, String.class);
        assert result != null;
        return ResponseEntity.ok(result);
	}
	

	public ResponseEntity<MessageResponse> deleteEmployee(String url) {
		restTemplate.delete(EMPLOYEE_URL+url);
        
        return ResponseEntity.ok(new MessageResponse("Employee deleted"));
	}




	public ResponseEntity<Object> getAllEmployees(String url) {
		Object result = restTemplate.getForObject(url,List.class);
		assert result != null;
        return ResponseEntity.ok(result);
	}




	public ResponseEntity<Object> getEmployeeById(String url) {
		Object result = restTemplate.getForObject(url,List.class);
		assert result != null;
        return ResponseEntity.ok(result);
	}



}
