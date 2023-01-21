package com.user.management.services;


import com.fasterxml.jackson.core.JsonProcessingException;


import com.user.management.payload.request.JobRequestPayload;
import com.user.management.payload.request.SignupRequest;
import com.user.management.payload.request.UpdateUserDetails;
import com.user.management.payload.response.MessageResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Component
public class RestApiCall {

    private static final String EMPLOYEE_URL = "http://localhost:8082/api/employee/manage";

    private static final String JOB_URL = "http://localhost:8083/api/job/manage";
    private static final Logger logger = LoggerFactory.getLogger(RestApiCall.class);

    @Autowired
    RestTemplate restTemplate;

    
    public ResponseEntity<String> saveEmployee(String url, SignupRequest request) {
        String result = restTemplate.postForObject(EMPLOYEE_URL+url, request, String.class);
        logger.debug(result);
        assert result != null;
        return ResponseEntity.ok(result);
    }
    
    


	public ResponseEntity<String> updateEmployee(String url, UpdateUserDetails userDetails) {
		String result = restTemplate.postForObject(EMPLOYEE_URL+url, userDetails, String.class);
        assert result != null;
        return ResponseEntity.ok(result);
	}
	

	public ResponseEntity<MessageResponse> deleteEmployee(String url) {
        logger.info("Inside RestAPICall deleteEmployee----{}",url);
        restTemplate.delete(EMPLOYEE_URL+url);
        return ResponseEntity.ok(new MessageResponse("Employee deleted"));
	}




	public ResponseEntity<Object> getAllEmployees(String url) {
        logger.info("Inside RestAPICall getAllEmployees----{}",url);
		Object result = restTemplate.getForObject(EMPLOYEE_URL+url,List.class);
		assert result != null;
        return ResponseEntity.ok(result);
	}




	public ResponseEntity<Object> getEmployeeById(String url) {
        logger.info("Inside RestAPICall getEmployeeById----{}",url);
		Object result = restTemplate.getForObject(EMPLOYEE_URL+url,Object.class);
		assert result != null;
        return ResponseEntity.ok(result);
	}


    public ResponseEntity<String> createJob(String url, JobRequestPayload payload) {
        String result = restTemplate.postForObject(JOB_URL+url,payload, String.class);
        logger.debug(result);
        assert result != null;
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<String> updateJob(String url, JobRequestPayload payload) {
        String result = restTemplate.postForObject(JOB_URL+url,payload, String.class);
        logger.debug(result);
        assert result != null;
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<Object> getAllJobs(String url) {
        logger.info("Inside RestAPICall getAllJobs----{}",url);
        Object result = restTemplate.getForObject(JOB_URL+url,List.class);
        assert result != null;
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<MessageResponse> deleteJob(String url) {
        logger.info("Inside RestAPICall deleteJob----{}",url);
        restTemplate.delete(JOB_URL+url);
        return ResponseEntity.ok(new MessageResponse("Job deleted"));
    }




    public ResponseEntity<?> processJob(String url) {
        logger.info("Inside RestAPICall processJob----{}",url);
        String result = restTemplate.getForObject(JOB_URL+url,String.class);
        assert result != null;
        return ResponseEntity.ok(result);
    }

}
