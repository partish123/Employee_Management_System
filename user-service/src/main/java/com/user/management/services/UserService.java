package com.user.management.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.user.management.models.*;
import com.user.management.payload.request.SignupRequest;
import com.user.management.payload.request.UpdateUserDetails;
import com.user.management.payload.response.MessageResponse;
import com.user.management.repository.RoleRepository;
import com.user.management.repository.UserRepository;
import com.user.management.utility.UserException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	private RestApiCall restClient;

//	@Autowired
//	private User user;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	public Object updateUser(UpdateUserDetails userDetails, Long id) throws UserException {

		try {
			Optional<User> user = userRepository.findById(id);

			if (userRepository.existsByUsername(userDetails.getUsername())) {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
			}

			if (userRepository.existsByEmail(userDetails.getEmail())) {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
			}

			if (user.isPresent() && user.get().getId() == id) {
				user.get().setUsername(userDetails.getUsername());
				user.get().setFirstname(userDetails.getFirstname());
				user.get().setLastname(userDetails.getLastname());
				user.get().setEmail(userDetails.getEmail());
				user.get().setPassword(encoder.encode(userDetails.getPassword()));

			}

			Set<String> strRoles = userDetails.getRole();
			Set<Role> roles = new HashSet<>();

			if (strRoles == null) {
				Optional<User> user1 = userRepository.findByUsername(userDetails.getUsername());

				roles.addAll(user1.get().getRoles());
			} else {
				strRoles.forEach(role -> {
					switch (role) {

					case "Developer":
						Role devRole = roleRepository.findByName(ERole.ROLE_DEVELOPER)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(devRole);

						break;

					case "Tester":
						Role testerRole = roleRepository.findByName(ERole.ROLE_TESTER)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(testerRole);

						break;

					case "Analyst":
						Role analystRole = roleRepository.findByName(ERole.ROLE_ANALYST)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(analystRole);

						break;

					default:
						Role userRole = roleRepository.findByName(ERole.ROLE_ANALYST)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(userRole);
					}
				});
			}

			user.get().setRoles(roles);
			userRepository.save(user.get());
			return new MessageResponse("User updated");
		} catch (Exception e) {
			throw new UserException("Something went wrong. Please try after some time!");
		}

	}

	public Object deleteUser(Long id) throws UserException {
		try {
			logger.info("Inside UserService deleteUser----{}",id);
			Optional<User> user = userRepository.findById(id);
			if(user.isPresent()) {
				userRepository.delete(user.get());
				return new MessageResponse("User deleted");
			}
			else
				return new MessageResponse("User not present");
			
		}catch (Exception e) {
			throw new UserException("Something went wrong. Please try after some time!");
		}
	}
	
	public Object getAllUsers() throws UserException {
		try {

			List<User> user = userRepository.findAll();
			if (!user.isEmpty()) {
				
				return user;
			} else
				throw new UserException("Users not found");

		} catch (Exception e) {
			throw new UserException("Something went wrong. Please try after some time!");
		}
	}
	
	
	public Object getUserById(Long id) throws UserException {
		try {

			Optional<User> user = userRepository.findById(id);
			if (user.isPresent()) {
				
				return user.get();
			} else
				return new MessageResponse("User not found");

		} catch (Exception e) {
			throw new UserException("Something went wrong. Please try after some time!");
		}
	}
	
	
	
	
	
	
	
	
	public Object saveEmployee(SignupRequest signUpRequest) throws UserException {
		logger.info("Inside saveEmployee----{}",signUpRequest);
		ResponseEntity<String> savedEmployee = restClient.saveEmployee("/createEmployee", signUpRequest);
		if (savedEmployee != null) {
			return savedEmployee;
		} else {
			throw new UserException("Something went wrong.. Please try after some time!");
		}

	}

	public Object updateEmployee(UpdateUserDetails userDetails, Long id) throws UserException {
		System.out.println("Inside UserService updateEmployee---{}"+ userDetails);
		ResponseEntity<String> updateEmployee = restClient.updateEmployee("/updateEmployee/" + id , userDetails);
		if (updateEmployee != null) {
			return updateEmployee;
		} else {
			throw new UserException("Something went wrong.. Please try after some time!");
		}

	}
	

	public Object deleteEmployee(Long id) throws UserException {
		logger.info("Inside UserService deleteEmployee----{}",id);
		return restClient.deleteEmployee("/deleteEmployee/"+id);
		
	}

	public Object getAllEmployees() throws UserException {
		logger.info("Inside UserService getAllEmployees----");
		ResponseEntity<Object> employeeList = restClient.getAllEmployees("/getAllEmployees");
		logger.info("List of employee----{}",employeeList);
		if (employeeList != null) {
			return employeeList;
		} else {
			throw new UserException("Something went wrong.. Please try after some time!");
		}
	}

	public Object getEmployeeById(String empId) throws UserException {
		logger.info("Inside UserService getEmployeeById----");
		ResponseEntity<Object> employee = restClient.getEmployeeById("/getEmployee/"+empId);
		logger.info("employee fetched details are----{}",employee);
		if (employee != null) {
			return employee;
		} else {
			throw new UserException("Something went wrong.. Please try after some time!");
		}
	}


	

	



}
