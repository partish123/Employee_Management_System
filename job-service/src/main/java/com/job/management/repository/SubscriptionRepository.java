package com.job.management.repository;

import com.job.management.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Job,Integer> {
    List<Job> findByEmailId(String emailId);
    List<Job> findByBookId(Integer bookId);

    List<Job> findByReaderId(Integer readerId);

}
