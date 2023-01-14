package com.employee.management.controllers;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.management.payload.request.CreateEmployeePayload;
import com.employee.management.payload.request.UpdateEmployeePayload;
import com.employee.management.payload.response.MessageResponse;
import com.employee.management.services.EmployeeService;
import com.employee.management.utility.EmployeeException;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/employee/manage")
public class EmployeeController {
	@Autowired
	EmployeeService service;
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

//	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")

	
    @PostMapping("/createEmployee")
    public ResponseEntity<String> createEmployee(@RequestBody CreateEmployeePayload payload) throws EmployeeException {
        try {
            logger.info("Inside EmployeeController createEmployee---{}",payload);
            MessageResponse m = service.createEmployee(payload);
            logger.info(m.getMessage());
            return new ResponseEntity<>("Employee added successfully!",HttpStatus.CREATED);
        }catch (Exception e){
            throw new EmployeeException("Sorry something went wrong in create employee ",e);
        }
    }
    
    
    @PostMapping("/updateEmployee/{id}")
    public ResponseEntity<String> updateEmployee(@RequestBody UpdateEmployeePayload payload, @PathVariable String id) throws EmployeeException {
        try {
            logger.info("Inside EmployeeController updateEmployee---");
            MessageResponse m = service.updateEmployee(payload,Long.parseLong(id));
            logger.info(m.getMessage());
            return new ResponseEntity<>("Employee added successfully!",HttpStatus.CREATED);
        }catch (Exception e){
            throw new EmployeeException("Sorry something went wrong in update employee",e);
        }
    }
    
    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String id) throws EmployeeException {
        try {
            logger.info("Inside EmployeeController deleteEmployee---");
            MessageResponse m = service.deleteEmployee(Long.parseLong(id));
            logger.info("Message---{}",m);
            return new ResponseEntity<>("Employee added successfully!",HttpStatus.CREATED);
        }catch (Exception e){
            throw new EmployeeException("Sorry something went wrong in delete employee",e);
        }
    }
	
    @GetMapping("/getAllEmployees")
    public ResponseEntity<?> getAllEmployees() throws EmployeeException {
        try {
            logger.info("Inside EmployeeController getAllEmployees---");
        	Object employeeList = service.getAllEmployees();
            logger.info("employeeList----{}",employeeList);
            if(employeeList!=null)
            return new ResponseEntity<>(employeeList,HttpStatus.OK);
            else
            	return ResponseEntity.status(500).body("Failed to retrieve list of employees!");
        }catch (Exception e){
            throw new EmployeeException("Sorry something went wrong in getAllEmployees",e);
        }
    }
    
    @GetMapping("/getEmployee/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable String id) throws EmployeeException {
        try {
            logger.info("Inside EmployeeController getEmployee---");
        	Object employee = service.getEmployee(Long.parseLong(id));
            logger.info("employee----{}",employee);
            if(employee!=null)
            return new ResponseEntity<>(employee,HttpStatus.OK);
            else
            	return ResponseEntity.status(500).body("Failed to retrieve employee!");
        }catch (Exception e){
            throw new EmployeeException("Sorry something went wrong in getEmployee ",e);
        }
    }




}
