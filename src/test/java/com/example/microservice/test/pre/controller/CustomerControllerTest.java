package com.example.microservice.test.pre.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.microservice.test.pre.constant.RequestStatus;
import com.example.microservice.test.pre.dto.CustomerIssueDTO;
import com.example.microservice.test.pre.model.CustomerIssue;
import com.example.microservice.test.pre.service.CustomerIssueService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

	@Mock
	private CustomerIssueService service;

	@InjectMocks
	private CustomerController customerController;

	private MockMvc mockMvc;

	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
		objectMapper = new ObjectMapper();
	}

	@Test
	void testGetCustomerIssues() throws Exception {
		// Given
		CustomerIssue issue = new CustomerIssue();
		issue.setIssueId(1L);
		issue.setDescription("issue 1");
		issue.setForwardTo("credit_card");
		issue.setStatus(RequestStatus.IN_PROGRESS);
		issue.setCreatedOn(LocalDateTime.now());
		issue.setModifierOn(LocalDateTime.now());

		List<CustomerIssue> issues = Arrays.asList(issue);
		when(service.getCustomerIssues()).thenReturn(issues);

		// When & Then
		mockMvc.perform(get("/api/v1/customer/issue").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.message").value("request successfully."))
				.andExpect(jsonPath("$.data[0].issueId").value(1))
				.andExpect(jsonPath("$.data[0].description").value("issue 1"))
				.andExpect(jsonPath("$.data[0].forwardTo").value("credit_card"))
				.andExpect(jsonPath("$.data[0].status").value("IN_PROGRESS"));
	}

	@Test
	void testGetCustomerIssueById() throws Exception {
		// Given
		CustomerIssue issue = new CustomerIssue();
		issue.setIssueId(1L);
		issue.setDescription("issue 1");
		issue.setForwardTo("credit_card");
		issue.setStatus(RequestStatus.IN_PROGRESS);
		issue.setCreatedOn(LocalDateTime.now());
		issue.setModifierOn(LocalDateTime.now());

		when(service.getCustomerIssuesById(1L)).thenReturn(issue);

		// When & Then
		mockMvc.perform(get("/api/v1/customer/issue/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.message").value("request successfully."))
				.andExpect(jsonPath("$.data.issueId").value(1))
				.andExpect(jsonPath("$.data.description").value("issue 1"))
				.andExpect(jsonPath("$.data.forwardTo").value("credit_card"))
				.andExpect(jsonPath("$.data.status").value("IN_PROGRESS"));
	}

	@Test
	void testCreateCustomerIssue() throws Exception {
		// Given
		CustomerIssueDTO dto = new CustomerIssueDTO();
		dto.setDescription("issue 1");
		dto.setForwardTo("credit_card");
		dto.setStatus(RequestStatus.IN_PROGRESS);

		CustomerIssue issue = new CustomerIssue();
		issue.setIssueId(1L);
		issue.setDescription(dto.getDescription());
		issue.setForwardTo(dto.getForwardTo());
		issue.setStatus(dto.getStatus());
		issue.setCreatedOn(LocalDateTime.now());
		issue.setModifierOn(LocalDateTime.now());

		when(service.createCustomerIssue(Mockito.any(CustomerIssueDTO.class))).thenReturn(issue);

		// When & Then
		mockMvc.perform(post("/api/v1/customer/issue").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto))).andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.message").value("request successfully."))
				.andExpect(jsonPath("$.data.issueId").value(1))
				.andExpect(jsonPath("$.data.description").value("issue 1"))
				.andExpect(jsonPath("$.data.forwardTo").value("credit_card"))
				.andExpect(jsonPath("$.data.status").value("IN_PROGRESS"));
	}

	@Test
	void testUpdateCustomerIssue() throws Exception {
		// Given
		CustomerIssueDTO dto = new CustomerIssueDTO();
		dto.setDescription("updated issue");
		dto.setForwardTo("credit_card");
		dto.setStatus(RequestStatus.COMPLETED);

		CustomerIssue issue = new CustomerIssue();
		issue.setIssueId(1L);
		issue.setDescription(dto.getDescription());
		issue.setForwardTo(dto.getForwardTo());
		issue.setStatus(dto.getStatus());
		issue.setCreatedOn(LocalDateTime.now());
		issue.setModifierOn(LocalDateTime.now());

		when(service.updateCustomerIssue(Mockito.eq(1L), Mockito.any(CustomerIssueDTO.class))).thenReturn(issue);

		// When & Then
		mockMvc.perform(put("/api/v1/customer/issue/1").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto))).andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.message").value("request successfully."))
				.andExpect(jsonPath("$.data.issueId").value(1))
				.andExpect(jsonPath("$.data.description").value("updated issue"))
				.andExpect(jsonPath("$.data.forwardTo").value("credit_card"))
				.andExpect(jsonPath("$.data.status").value("COMPLETED"));
	}
}
