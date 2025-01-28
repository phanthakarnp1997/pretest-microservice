package com.example.microservice.test.pre.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.microservice.test.pre.dto.CustomerIssueDTO;
import com.example.microservice.test.pre.exception.IssueNotFoundException;
import com.example.microservice.test.pre.model.CustomerIssue;
import com.example.microservice.test.pre.repository.CustomerIssueRepository;

@Service
public class CustomerIssueService {

	private final CustomerIssueRepository repository;

	public CustomerIssueService(CustomerIssueRepository repository) {
		this.repository = repository;
	}

	public List<CustomerIssue> getCustomerIssues() {
		return repository.findAll();
	}

	public CustomerIssue getCustomerIssuesById(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new IssueNotFoundException(
						"Issue Id=" + id + " Not found."));
	}

	public CustomerIssue createCustomerIssue(CustomerIssueDTO dto) {
		CustomerIssue savedItem = CustomerIssue.builder()
				.description(dto.getDescription()).forwardTo(dto.getForwardTo())
				.status(dto.getStatus()).build();

		// Forward to another service

		return repository.save(savedItem);
	}

	public CustomerIssue updateCustomerIssue(Long id, CustomerIssueDTO dto) {
		CustomerIssue customerIssue = repository.findById(id)
				.orElseThrow(() -> new IssueNotFoundException(
						"Issue Id=" + id + " Not found."));

		customerIssue.setStatus(dto.getStatus());
		
		// Triggered to another service

		return repository.save(customerIssue);
	}
}
