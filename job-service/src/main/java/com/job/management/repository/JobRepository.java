package com.job.management.repository;


import com.job.management.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;



@Repository
public interface JobRepository extends JpaRepository<Job,Long> {

}
