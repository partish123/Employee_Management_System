package com.job.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.job.management.model.Subscription;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription,Integer> {
    List<Subscription> findByEmailId(String emailId);
    List<Subscription> findByBookId(Integer bookId);

    List<Subscription> findByReaderId(Integer readerId);

}
