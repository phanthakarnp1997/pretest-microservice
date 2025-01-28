package com.example.microservice.test.pre.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.microservice.test.pre.dto.ApiResponse;
import com.example.microservice.test.pre.dto.CustomerIssueDTO;
import com.example.microservice.test.pre.model.CustomerIssue;
import com.example.microservice.test.pre.service.CustomerIssueService;

@RestController
@RequestMapping("/api/v1/customer/issue")
public class CustomerController {

	private final CustomerIssueService service;

	public CustomerController(CustomerIssueService service) {
		this.service = service;
	}

	@GetMapping()
	public ResponseEntity<ApiResponse<List<CustomerIssue>>> getCustomerIssues() {
		return ResponseEntity
				.ok(ApiResponse.success(service.getCustomerIssues()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<CustomerIssue>> getCustomerIssueById(
			@PathVariable("id") Long id) {
		return ResponseEntity
				.ok(ApiResponse.success(service.getCustomerIssuesById(id)));
	}

	@PostMapping()
	public ResponseEntity<ApiResponse<CustomerIssue>> createCustomerIssue(
			@RequestBody CustomerIssueDTO dto) {
		return ResponseEntity
				.ok(ApiResponse.success(service.createCustomerIssue(dto)));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<CustomerIssue>> updateCustomerIssue(
			@PathVariable("id") Long id, @RequestBody CustomerIssueDTO dto) {
		return ResponseEntity
				.ok(ApiResponse.success(service.updateCustomerIssue(id, dto)));
	}
}
