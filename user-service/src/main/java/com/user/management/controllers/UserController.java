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
			System.out.println("Inside UserController updateUser-");
			Object updateUser = userService.updateUser(userDetails,Long.parseLong(id));
			System.out.println("Inside updateUser--{}"+ updateUser);
			Object updateEmployee = userService.updateEmployee(userDetails,Long.parseLong(id));
		    System.out.println("Inside updateUser--{}"+ updateEmployee);
			if (updateUser != null && updateEmployee !=null)
			  return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
			else
				throw new UserException("User not updated--->");

		}

		catch (Exception e) {
			throw new UserException("User details not updated...got error--->", e);
		}

	}

	@DeleteMapping(value = "/deleteUser/{id}")
//	@PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
	public ResponseEntity<?> deleteUser(@PathVariable String id) throws UserException {

		try {
			System.out.println("Inside UserController deleteUser-");
			Object message1 = userService.deleteUser(Long.parseLong(id));
			Object message2 = userService.deleteEmployee(Long.parseLong(id));
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
			System.out.println("Inside UserController getAllUsers-");
        	Object userList = userService.getAllUsers();
            if(userList!=null) {
				System.out.println("Inside UserController getAllUsers-"+userList);
				return new ResponseEntity<>(userList, HttpStatus.OK);
			}
            else
            	return ResponseEntity.status(500).body("Failed to retrieve list of users!");
        }catch (Exception e){
            throw new UserException("Failed to retrieve list of users",e);
        }
    }
    
    
    @GetMapping("/getUser/{id}")
    public ResponseEntity<?> getUserById(String id) throws UserException {
        try {
			System.out.println("Inside UserController getUserById-");
        	Object user = userService.getUserById(Long.parseLong(id));
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
			System.out.println("Inside UserController getAllEmployees-");
			Object allUsers = userService.getAllEmployees();
			if (allUsers != null)
				return ResponseEntity.status(200).body(allUsers);
			else
				throw new UserException("Users not found--->");

		} catch (Exception e) {
			throw new UserException("Users not found..got error--->", e);
		}
	}
	

	@GetMapping(value = "/getEmployee/{id}")
//	@PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
	public ResponseEntity<?> getEmployee( @PathVariable String id) throws UserException {

		try {
			System.out.println("Inside UserController getEmployee-");
			Object user = userService.getEmployeeById(id);
			if (user != null)
				return ResponseEntity.status(200).body(user);
			else
				throw new UserException("User not found---->");

		} catch (Exception e) {
			throw new UserException("User not found..got error--->", e);
		}
	}



}
