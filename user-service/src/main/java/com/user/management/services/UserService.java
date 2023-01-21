package com.user.management.services;


import com.user.management.models.*;
import com.user.management.payload.request.JobRequestPayload;
import com.user.management.payload.request.SignupRequest;
import com.user.management.payload.request.UpdateUserDetails;
import com.user.management.payload.response.MessageResponse;
import com.user.management.repository.RoleRepository;
import com.user.management.repository.UserRepository;
import com.user.management.utility.UserException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;

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


    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	public Object updateUser(UpdateUserDetails userDetails, Long id) throws UserException {

		try {
			Optional<User> user = userRepository.findById(id);

			if(user.isPresent()) {

//				if (userRepository.existsByUsername(userDetails.getUsername())) {
//					return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
//				}
//
//				if (userRepository.existsByEmail(userDetails.getEmail())) {
//					return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
//				}

				if (Objects.equals(user.get().getId(), id)) {
					if (userDetails.getUsername() != null && !userDetails.getUsername().isEmpty()) {
						user.get().setUsername(userDetails.getUsername());
					}
					if (userDetails.getFirstname() != null && !userDetails.getFirstname().isEmpty()) {
						user.get().setFirstname(userDetails.getFirstname());
					}
					if (userDetails.getLastname() != null && !userDetails.getLastname().isEmpty()) {
						user.get().setLastname(userDetails.getLastname());
					}
					if (userDetails.getEmail() != null && !userDetails.getEmail().isEmpty()) {
						user.get().setEmail(userDetails.getEmail());
					}
					if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
						user.get().setPassword(encoder.encode(userDetails.getPassword()));
					}
				}

				Set<String> strRoles = userDetails.getRole();
				Set<Role> roles = new HashSet<>();

				if (strRoles == null) {
					Optional<User> user1 = userRepository.findByUsername(userDetails.getUsername());
					if (user1.isPresent() && user1.get().getRoles() != null && !user1.get().getRoles().isEmpty()) {
						roles.addAll(user1.get().getRoles());
					}
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

//					default:
//						Role userRole = roleRepository.findByName(ERole.ROLE_ANALYST)
//								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//						roles.add(userRole);
						}
					});
					user.get().setRoles(roles);
				}

				userRepository.save(user.get());
				return new MessageResponse("User updated");
			}
			else{
				throw new UserException("User not found in DB!");
			}
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
		logger.info("List of employee----{}",employeeList.getBody());
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


	public ResponseEntity<String> createJob(JobRequestPayload payload) throws UserException{
		logger.info("Inside UserService createJob----{}",payload);

		Set<String> strRoles = Collections.singleton(payload.getRole());


		strRoles.forEach(role -> {
			switch (role) {

				case "Developer":
					Optional<Role> devRole = roleRepository.findByName(ERole.ROLE_DEVELOPER);
					if(devRole.isPresent()) {
						payload.setRole(String.valueOf(ERole.ROLE_DEVELOPER));
					}
					break;

				case "Tester":
					Optional<Role> testerRole = roleRepository.findByName(ERole.ROLE_TESTER);
						if(testerRole.isPresent()) {
							payload.setRole(String.valueOf(ERole.ROLE_TESTER));
						}
					break;

				case "Analyst":
					Optional<Role> analystRole = roleRepository.findByName(ERole.ROLE_ANALYST);
							if(analystRole.isPresent()) {
								payload.setRole(String.valueOf(ERole.ROLE_ANALYST));
							}
					break;


			}
		});


		logger.info("Inside UserService createJob...after payload role change----{}",payload);

		ResponseEntity<String> savedJob = restClient.createJob("/createJob", payload);
		if (savedJob != null) {
			return savedJob;
		} else {
			throw new UserException("Something went wrong.. Please try after some time!");
		}
	}

	public ResponseEntity<String> updateJob(JobRequestPayload payload,Long id) throws UserException {
		System.out.println("Inside UserService updateJob---{}"+ payload);

		Set<String> strRoles = Collections.singleton(payload.getRole());


		strRoles.forEach(role -> {
			switch (role) {

				case "Developer":
					Optional<Role> devRole = roleRepository.findByName(ERole.ROLE_DEVELOPER);
					if(devRole.isPresent()) {
						payload.setRole(String.valueOf(ERole.ROLE_DEVELOPER));
					}
					break;

				case "Tester":
					Optional<Role> testerRole = roleRepository.findByName(ERole.ROLE_TESTER);
					if(testerRole.isPresent()) {
						payload.setRole(String.valueOf(ERole.ROLE_TESTER));
					}
					break;

				case "Analyst":
					Optional<Role> analystRole = roleRepository.findByName(ERole.ROLE_ANALYST);
					if(analystRole.isPresent()) {
						payload.setRole(String.valueOf(ERole.ROLE_ANALYST));
					}
					break;


			}
		});

		logger.info("Inside UserService updateJob...after payload role change----{}",payload);



		ResponseEntity<String> updateJob = restClient.updateJob("/updateJob/" + id , payload);
		if (updateJob != null) {
			return updateJob;
		} else {
			throw new UserException("Something went wrong.. Please try after some time!");
		}

	}


	public Object getAllJobs() throws UserException {
		logger.info("Inside UserService getAllJobs----");
		ResponseEntity<Object> jobList = restClient.getAllJobs("/getAllJobs");
		logger.info("List of jobs----{}",jobList.getBody());
		if (jobList != null) {
			return jobList;
		} else {
			throw new UserException("Something went wrong.. Please try after some time!");
		}
	}

	public Object deleteJob(Long id) throws UserException {
		logger.info("Inside UserService deleteJob----{}",id);
		return restClient.deleteJob("/deleteJob/"+id);

	}


	public ResponseEntity<?> processJob(String jobId, String userId,String status,String role) throws UserException {
		System.out.println("Inside UserService processJob---{}"+ jobId);
		ResponseEntity<?> processJob = restClient.processJob("/processJob/" + jobId + "/"+ userId + "/"+ status + "/" + role);
		if (processJob != null) {
			return processJob;
		} else {
			throw new UserException("Job not processed...either selected job not available or your role is not applicable!");
		}
	}
}
