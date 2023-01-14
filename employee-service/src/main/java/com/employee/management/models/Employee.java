package com.employee.management.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
@Table(	name = "employee")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "employee_id")
	private Long id;

	@NotBlank
	@Size(max = 20)
	@Column(name = "firstname")
	private String firstname;
	
	@NotBlank
	@Size(max = 20)
	@Column(name = "lastname")
	private String lastname;
	

	@NotBlank
	@Size(max = 50)
	@Email
	@Column(name = "email")
	private String email;

	
	@Column(name = "salary")
    private double salary;
    
	
//	private Set<Long> allocatedJobs;
	
	public Employee() {
		
	}
    


	public Employee(String firstname, String lastname, String email ) {
		this.firstname = firstname;
		this.email = email;
		this.lastname = lastname;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Employee{" +
				"id=" + id +
				", firstname='" + firstname + '\'' +
				", lastname='" + lastname + '\'' +
				", email='" + email + '\'' +
				", salary=" + salary +
				'}';
	}
}
