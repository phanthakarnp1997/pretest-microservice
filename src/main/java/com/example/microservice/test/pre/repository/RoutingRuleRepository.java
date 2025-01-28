package com.example.microservice.test.pre.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microservice.test.pre.model.RoutingRule;

public interface RoutingRuleRepository extends JpaRepository<RoutingRule, String> {

}
