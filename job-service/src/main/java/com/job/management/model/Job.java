package com.job.management.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
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

}
