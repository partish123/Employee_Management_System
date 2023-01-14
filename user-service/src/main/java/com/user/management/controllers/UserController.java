package com.user.management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import com.user.management.models.ERole;

import com.user.management.models.Role;

import com.user.management.models.User;
import com.user.management.payload.request.SignupRequest;
import com.user.management.payload.request.UpdateUserDetails;
import com.user.management.payload.response.MessageResponse;
import com.user.management.repository.RoleRepository;
import com.user.management.repository.UserRepository;
import com.user.management.security.jwt.JwtUtils;
import com.user.management.services.UserService;
import com.user.management.utility.UserException;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user/manage")
public class UserController {
	@Autowired
	UserService userService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;


	@PostMapping(value = "/updateUser/{id}")
	public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserDetails userDetails, @PathVariable String id)
			throws UserException {
		try {
			
			
			Object updateUser = userService.updateUser(userDetails,Long.parseLong(id));
			Object updateEmployee = userService.updateEmployee(userDetails,Long.parseLong(id));
			
			if (updateUser != null && updateEmployee !=null)
			  return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
			else
				throw new UserException("Book not updated--->");

		}

		catch (Exception e) {
			throw new UserException("User details not updated...got error--->", e);
		}

	}

	@DeleteMapping(value = "/deleteUser/{userId}")
//	@PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
	public ResponseEntity<?> deleteUser(@PathVariable Long id) throws UserException {

		try {
			Object message1 = userService.deleteUser(id);
			Object message2 = userService.deleteEmployee(id);
			if (message1 != null && message2!=null)
				return ResponseEntity.status(200).body("User deleted successfully");
			else
				throw new UserException("User not deleted--->");

		} catch (Exception e) {
			throw new UserException("User is not deleted..got error--->", e);
		}
	}
	
	
	
    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers() throws UserException {
        try {
        	Object userList = userService.getAllUsers();
            if(userList!=null)
            return new ResponseEntity<>(userList,HttpStatus.OK);
            else
            	return ResponseEntity.status(500).body("Failed to retrieve list of users!");
        }catch (Exception e){
            throw new UserException("Failed to retrieve list of users",e);
        }
    }
    
    
    @GetMapping("/getUser/{userId}")
    public ResponseEntity<?> getUserById(Long id) throws UserException {
        try {
        	Object user = userService.getUserById(id);
            if(user!=null)
            return new ResponseEntity<>(user,HttpStatus.OK);
            else
            	return ResponseEntity.status(500).body("Failed to retrieve user!");
        }catch (Exception e){
            throw new UserException("Sorry something went wrong in create ",e);
        }
    }
	
	
	

	@GetMapping(value = "/getAllEmployees")
//	@PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
	public ResponseEntity<?> getAllEmployees() throws UserException {

		try {
			Object allUsers = userService.getAllEmployees();
			if (allUsers != null)
				return ResponseEntity.status(200).body(allUsers);
			else
				throw new UserException("Users not found--->");

		} catch (Exception e) {
			throw new UserException("Users not found..got error--->", e);
		}
	}
	

	@GetMapping(value = "/getEmployee/{empId}")
//	@PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
	public ResponseEntity<?> getEmployee( @PathVariable String empId) throws UserException {

		try {
			Object user = userService.getEmployeeById(empId);
			if (user != null)
				return ResponseEntity.status(200).body(user);
			else
				throw new UserException("User not found---->");

		} catch (Exception e) {
			throw new UserException("User not found..got error--->", e);
		}
	}



}
