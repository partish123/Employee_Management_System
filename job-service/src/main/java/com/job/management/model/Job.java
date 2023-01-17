package com.job.management.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(	name = "job")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private Long id;

    @NotBlank
    @Size(max = 20)
    @Column(name = "job_name")
    private String name;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "job_created_date")
    private LocalDate jobCreatedDate;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "job_updated_date")
    private LocalDate jobUpdatedDate;


    @JsonFormat(pattern = "HH:mm")
    @Column(name = "start_time")
    private LocalTime starttime;

    @JsonFormat(pattern = "HH:mm")
    @Column(name = "end_time")
    private LocalTime endtime;

    @Column(name = "profit_value")
    private double profitValue;


    @Size(max = 50)
    @Column(name = "applicable_role")
    private String role;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Status status;

    @Column(name = "employee_id")
    private Long employeeId;

    public Job() {
    }

    public Job(String name, LocalDate jobCreatedDate, LocalDate jobUpdatedDate, LocalTime starttime, LocalTime endtime, double profitValue, String role, Status status, Long employeeId) {
        this.name = name;
        this.jobCreatedDate = jobCreatedDate;
        this.jobUpdatedDate = jobUpdatedDate;
        this.starttime = starttime;
        this.endtime = endtime;
        this.profitValue = profitValue;
        this.role = role;
        this.status = status;
        this.employeeId = employeeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getJobCreatedDate() {
        return jobCreatedDate;
    }

    public void setJobCreatedDate(LocalDate jobCreatedDate) {
        this.jobCreatedDate = jobCreatedDate;
    }

    public LocalDate getJobUpdatedDate() {
        return jobUpdatedDate;
    }

    public void setJobUpdatedDate(LocalDate jobUpdatedDate) {
        this.jobUpdatedDate = jobUpdatedDate;
    }

    public LocalTime getStarttime() {
        return starttime;
    }

    public void setStarttime(LocalTime starttime) {
        this.starttime = starttime;
    }

    public LocalTime getEndtime() {
        return endtime;
    }

    public void setEndtime(LocalTime endtime) {
        this.endtime = endtime;
    }

    public double getProfitValue() {
        return profitValue;
    }

    public void setProfitValue(double profitValue) {
        this.profitValue = profitValue;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", jobCreatedDate=" + jobCreatedDate +
                ", jobUpdatedDate=" + jobUpdatedDate +
                ", starttime=" + starttime +
                ", endtime=" + endtime +
                ", profitValue=" + profitValue +
                ", role='" + role + '\'' +
                ", status=" + status +
                '}';
    }
}
