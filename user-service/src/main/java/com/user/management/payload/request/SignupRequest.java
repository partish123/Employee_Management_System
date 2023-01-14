package com.user.management.payload.request;

import java.util.Set;

import javax.validation.constraints.*;
 
public class SignupRequest {
//	@NotBlank
//	private long userId;
		
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
    
    @NotBlank
    @Size(min = 2, max = 15)
    private String firstname;
    
    @NotBlank
    @Size(min = 2, max = 15)
    private String lastname;
 
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    
 //   private Set<String> role;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
  
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    
//    public Set<String> getRole() {
//      return this.role;
//    }
//    
//    public void setRole(Set<String> role) {
//      this.role = role;
//    }

//	public long getUserId() {
//		return userId;
//	}
//
//	public void setUserId(long userId) {
//		this.userId = userId;
//	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


    @Override
    public String toString() {
        return "SignupRequest{" +
                "username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
