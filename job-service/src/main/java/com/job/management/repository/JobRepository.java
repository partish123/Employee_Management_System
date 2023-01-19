package com.job.management.repository;


import com.job.management.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface JobRepository extends JpaRepository<Job,Long> {
    List<Job> findByEmployeeId(Long employeeId);
}
