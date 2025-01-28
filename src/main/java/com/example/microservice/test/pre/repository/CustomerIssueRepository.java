package com.example.microservice.test.pre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.microservice.test.pre.model.CustomerIssue;

@Repository
public interface CustomerIssueRepository extends JpaRepository<CustomerIssue, Long> {

}
