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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/customer/issue")
@Tag(name = "Customer Issue", description = "APIs for Customer Issue Management")
public class CustomerController {

	private final CustomerIssueService service;

	public CustomerController(CustomerIssueService service) {
		this.service = service;
	}

	@GetMapping()
	@Operation(summary = "Get all issues", description = "Retrieve all issues")
	@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successful response", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Customer Issue Example", value = "{\r\n"
			+ "  \"success\": true,\r\n"
			+ "  \"message\": \"request successfully.\",\r\n"
			+ "  \"data\": [\r\n" + "    {\r\n" + "      \"issueId\": 1,\r\n"
			+ "      \"description\": \"issue 1\",\r\n"
			+ "      \"forwardTo\": \"credit-card-issue\",\r\n"
			+ "      \"status\": \"IN_PROGRESS\",\r\n"
			+ "      \"createdOn\": \"2025-01-28T16:11:20.72\",\r\n"
			+ "      \"modifierOn\": \"2025-01-28T16:11:20.72\"\r\n"
			+ "    }\r\n" + "  ]\r\n" + "}")))
	public ResponseEntity<ApiResponse<List<CustomerIssue>>> getCustomerIssues() {
		return ResponseEntity
				.ok(ApiResponse.success(service.getCustomerIssues()));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get issue by id", description = "Retrieve issue by id")
	@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successful response", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Customer Issue Example", value = "{\"success\":true,\"message\":\"request successfully.\",\"data\":{\"issueId\":1,\"description\":\"issue 1\",\"forwardTo\":\"credit-card-issue\",\"status\":\"IN_PROGRESS\",\"createdOn\":\"2025-01-28T16:12:20.983\",\"modifierOn\":\"2025-01-28T16:12:20.983\"}}")))
	public ResponseEntity<ApiResponse<CustomerIssue>> getCustomerIssueById(
			@Parameter(description = "Issue id", example = "123") @PathVariable("id") Long id) {
		return ResponseEntity
				.ok(ApiResponse.success(service.getCustomerIssuesById(id)));
	}

	@PostMapping()
	@Operation(summary = "Create issue", description = "Create issue")
	public ResponseEntity<ApiResponse<CustomerIssue>> createCustomerIssue(
			@RequestBody CustomerIssueDTO dto) {
		return ResponseEntity
				.ok(ApiResponse.success(service.createCustomerIssue(dto)));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Modifier issue status", description = "Modifier issue status")
	public ResponseEntity<ApiResponse<CustomerIssue>> updateCustomerIssue(
			@Parameter(description = "Issue id", example = "123")
			@PathVariable("id") Long id, @RequestBody CustomerIssueDTO dto) {
		return ResponseEntity
				.ok(ApiResponse.success(service.updateCustomerIssue(id, dto)));
	}
}
