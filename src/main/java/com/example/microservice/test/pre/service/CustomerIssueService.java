package com.example.microservice.test.pre.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.microservice.test.pre.dto.CustomerIssueDTO;
import com.example.microservice.test.pre.exception.IssueNotFoundException;
import com.example.microservice.test.pre.model.CustomerIssue;
import com.example.microservice.test.pre.repository.CustomerIssueRepository;

@Service
public class CustomerIssueService {

	private final CustomerIssueRepository customerIssueRepository;
	private final ForwardingService forwardingService;

	public CustomerIssueService(CustomerIssueRepository customerIssueRepository, ForwardingService forwardingService) {
		this.customerIssueRepository = customerIssueRepository;
		this.forwardingService = forwardingService;
	}

	public List<CustomerIssue> getCustomerIssues() {
		return customerIssueRepository.findAll();
	}

	public CustomerIssue getCustomerIssuesById(Long id) {
		return customerIssueRepository.findById(id)
				.orElseThrow(() -> new IssueNotFoundException("Issue Id=" + id + " Not found."));
	}

	public CustomerIssue createCustomerIssue(CustomerIssueDTO dto) {
		CustomerIssue savedItem = CustomerIssue.builder().description(dto.getDescription())
				.forwardTo(dto.getForwardTo()).status(dto.getStatus()).build();

		// Forward to another service
		forwardingService.forwardRequest(dto);

		return customerIssueRepository.save(savedItem);
	}

	public CustomerIssue updateCustomerIssue(Long id, CustomerIssueDTO dto) {
		CustomerIssue customerIssue = customerIssueRepository.findById(id)
				.orElseThrow(() -> new IssueNotFoundException("Issue Id=" + id + " Not found."));

		customerIssue.setStatus(dto.getStatus());

		// Triggered to another service

		return customerIssueRepository.save(customerIssue);
	}
}
