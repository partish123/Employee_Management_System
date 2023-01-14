package com.user.management.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.management.models.ERole;
import com.user.management.models.Role;
import com.user.management.models.User;
import com.user.management.payload.request.LoginRequest;
import com.user.management.payload.request.SignupRequest;
import com.user.management.payload.response.JwtResponse;
import com.user.management.payload.response.MessageResponse;
import com.user.management.repository.RoleRepository;
import com.user.management.repository.UserRepository;
import com.user.management.security.jwt.JwtUtils;
import com.user.management.security.services.UserDetailsImpl;
import com.user.management.services.UserService;
import com.user.management.utility.UserException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	UserService userService;

	@PostMapping("/sign-in")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(),
												 roles
												));
	}

	@PostMapping("/sign-up")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws UserException {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

//		if (signUpRequest != null && signUpRequest.getRole() != null && !signUpRequest.getRole().isEmpty()) {
//			for (String s : signUpRequest.getRole()) {
//				if (!s.equalsIgnoreCase("admin") && !s.equalsIgnoreCase("user") && !s.equalsIgnoreCase("author")
//						&& !s.equalsIgnoreCase("reader")) {
//					return ResponseEntity.badRequest().body(new MessageResponse("Error: Role is Not Valid!"));
//				}
//			}
//		}

		// Create new user's account
//		User user = new User(signUpRequest.getUsername(), 
//							 signUpRequest.getEmail(),
//							 encoder.encode(signUpRequest.getPassword())
//							 
//							 );
		
		User user = new User(signUpRequest.getUsername(), signUpRequest.getFirstname(),signUpRequest.getLastname(),
				 signUpRequest.getEmail(),
				 encoder.encode(signUpRequest.getPassword())
				 );
		
		userRepository.save(user);
		userService.saveEmployee(signUpRequest);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));

//		Set<String> strRoles = signUpRequest.getRole();
//		Set<Role> roles = new HashSet<>();
//
//		if (strRoles == null) {
//			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//			roles.add(userRole);
//		} else {
//			strRoles.forEach(role -> {
//				switch (role) {
//					
//				case "Reader":
//					Role readerRole = roleRepository.findByName(ERole.ROLE_READER)
//							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//					roles.add(readerRole);
//
//					break;
//					
//				case "Author":
//					Role authorRole = roleRepository.findByName(ERole.ROLE_AUTHOR)
//							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//					roles.add(authorRole);
//
//					break;
//					
//				default:
//					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//					roles.add(userRole);
//				}
//			});
//		}
//
//		user.setRoles(roles);
		
		
	}
}
