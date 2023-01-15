package com.employee.management.services;

import com.employee.management.models.*;
import com.employee.management.payload.request.CreateEmployeePayload;
import com.employee.management.payload.request.UpdateEmployeePayload;
import com.employee.management.payload.response.MessageResponse;
import com.employee.management.repository.EmployeeRepository;
import com.employee.management.utility.EmployeeException;
import com.fasterxml.jackson.core.JsonProcessingException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
	@Autowired
	EmployeeRepository employeeRepository;

//	@Autowired
//	Employee employee;

	private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

	public MessageResponse createEmployee(CreateEmployeePayload payload) throws EmployeeException {
		if (payload != null) {
			logger.info("Inside EmployeeService createEmployee----{}",payload);
			Employee employee = new Employee();
			employee.setFirstname(payload.getFirstname());
			employee.setLastname(payload.getLastname());
			employee.setEmail(payload.getEmail());
			employeeRepository.save(employee);
			return new MessageResponse("Employee added successfully");
		} else {
			throw new EmployeeException("Something went wrong.. Please try after some time!");
		}

	}

	public MessageResponse updateEmployee(UpdateEmployeePayload payload, Long id) throws EmployeeException {
		logger.info("Inside EmployeeService updateEmployee----{}",payload);
		Optional<Employee> employee = employeeRepository.findById(id);
		if (payload != null && employee.isPresent()) {
			if(payload.getFirstname()!=null && !payload.getFirstname().isEmpty()) {
				employee.get().setFirstname(payload.getFirstname());
			}
			if(payload.getLastname()!=null && !payload.getLastname().isEmpty()) {
				employee.get().setLastname(payload.getLastname());
			}
			if(payload.getEmail()!=null && !payload.getEmail().isEmpty()) {
				employee.get().setEmail(payload.getEmail());
			}
			employeeRepository.save(employee.get());
			return new MessageResponse("Employee updated successfully");
		} else {
			throw new EmployeeException("Something went wrong.. Please try after some time!");
		}

	}

	public MessageResponse deleteEmployee(Long id) throws EmployeeException{
		try {
			logger.info("Inside EmployeeService deleteEmployee----{}",id);
			Optional<Employee> user = employeeRepository.findById(id);
			if (user.isPresent()) {
				employeeRepository.delete(user.get());
				return new MessageResponse("Employee deleted");
			} else
				return new MessageResponse("Employee not present");

		} catch (Exception e) {
			throw new EmployeeException("Something went wrong. Please try after some time!");
		}
	}

	public Object getAllEmployees() throws EmployeeException {
		try {

			List<Employee> user = employeeRepository.findAll();
			if (!user.isEmpty()) {
				
				return user;
			} else
				return new MessageResponse("Employees not found");

		} catch (Exception e) {
			throw new EmployeeException("Something went wrong. Please try after some time!");
		}
	}

	public Object getEmployee(Long id)  throws EmployeeException{
		try {

			Optional<Employee> user = employeeRepository.findById(id);
			if (user.isPresent()) {
				
				return user.get();
			} else
				return new MessageResponse("Employee not found");

		} catch (Exception e) {
			throw new EmployeeException("Something went wrong. Please try after some time!");
		}
	}
	
	
	



}
