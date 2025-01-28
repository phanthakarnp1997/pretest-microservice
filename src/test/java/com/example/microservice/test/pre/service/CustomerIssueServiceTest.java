package com.example.microservice.test.pre.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.microservice.test.pre.constant.RequestStatus;
import com.example.microservice.test.pre.dto.CustomerIssueDTO;
import com.example.microservice.test.pre.exception.IssueNotFoundException;
import com.example.microservice.test.pre.model.CustomerIssue;
import com.example.microservice.test.pre.repository.CustomerIssueRepository;

@ExtendWith(MockitoExtension.class)
class CustomerIssueServiceTest {

	@Mock
	private CustomerIssueRepository customerIssueRepository;

	@Mock
	private ForwardingService forwardingService;

	@InjectMocks
	private CustomerIssueService customerIssueService;

	private CustomerIssue customerIssue;
	private CustomerIssueDTO customerIssueDTO;

	@BeforeEach
	void setUp() {
		customerIssue = CustomerIssue.builder().issueId(1L).description("Test Issue").forwardTo("Department A")
				.status(RequestStatus.IN_PROGRESS).createdOn(LocalDateTime.now()).modifierOn(LocalDateTime.now())
				.build();

		customerIssueDTO = new CustomerIssueDTO();
		customerIssueDTO.setDescription("Test Issue");
		customerIssueDTO.setForwardTo("Department A");
		customerIssueDTO.setStatus(RequestStatus.IN_PROGRESS);
	}

	@Test
	void whenGetCustomerIssues_thenReturnList() {
		// given
		List<CustomerIssue> issues = Arrays.asList(customerIssue);
		when(customerIssueRepository.findAll()).thenReturn(issues);

		// when
		List<CustomerIssue> result = customerIssueService.getCustomerIssues();

		// then
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(customerIssue.getDescription(), result.get(0).getDescription());
		verify(customerIssueRepository).findAll();
	}

	@Test
	void whenGetCustomerIssuesById_thenReturnCustomerIssue() {
		// given
		when(customerIssueRepository.findById(1L)).thenReturn(Optional.of(customerIssue));

		// when
		CustomerIssue result = customerIssueService.getCustomerIssuesById(1L);

		// then
		assertNotNull(result);
		assertEquals(customerIssue.getDescription(), result.getDescription());
		assertEquals(customerIssue.getStatus(), result.getStatus());
		verify(customerIssueRepository).findById(1L);
	}

	@Test
	void whenGetCustomerIssuesById_thenThrowException() {
		// given
		when(customerIssueRepository.findById(99L)).thenReturn(Optional.empty());

		// when & then
		assertThrows(IssueNotFoundException.class, () -> {
			customerIssueService.getCustomerIssuesById(99L);
		});
		verify(customerIssueRepository).findById(99L);
	}

	@Test
	void whenCreateCustomerIssue_thenReturnSavedIssue() {
		// given
		CustomerIssue savedIssue = CustomerIssue.builder().issueId(1L).description(customerIssueDTO.getDescription())
				.forwardTo(customerIssueDTO.getForwardTo()).status(customerIssueDTO.getStatus()).build();

		when(customerIssueRepository.save(any(CustomerIssue.class))).thenReturn(savedIssue);

		// when
		CustomerIssue result = customerIssueService.createCustomerIssue(customerIssueDTO);

		// then
		assertNotNull(result);
		assertEquals(customerIssueDTO.getDescription(), result.getDescription());
		assertEquals(customerIssueDTO.getStatus(), result.getStatus());
		verify(customerIssueRepository).save(any(CustomerIssue.class));
		verify(forwardingService).forwardRequest(customerIssueDTO);
	}

	@Test
	void whenUpdateCustomerIssue_thenReturnUpdatedIssue() {
		// given
		CustomerIssueDTO updateDTO = new CustomerIssueDTO();
		updateDTO.setStatus(RequestStatus.IN_PROGRESS);

		CustomerIssue updatedIssue = CustomerIssue.builder().issueId(1L).description(customerIssue.getDescription())
				.forwardTo(customerIssue.getForwardTo()).status(RequestStatus.IN_PROGRESS).build();

		when(customerIssueRepository.findById(1L)).thenReturn(Optional.of(customerIssue));
		when(customerIssueRepository.save(any(CustomerIssue.class))).thenReturn(updatedIssue);

		// when
		CustomerIssue result = customerIssueService.updateCustomerIssue(1L, updateDTO);

		// then
		assertNotNull(result);
		assertEquals(RequestStatus.IN_PROGRESS, result.getStatus());
		verify(customerIssueRepository).findById(1L);
		verify(customerIssueRepository).save(any(CustomerIssue.class));
	}

	@Test
	void whenUpdateCustomerIssue_thenThrowException() {
		// given
		CustomerIssueDTO updateDTO = new CustomerIssueDTO();
		when(customerIssueRepository.findById(99L)).thenReturn(Optional.empty());

		// when & then
		assertThrows(IssueNotFoundException.class, () -> {
			customerIssueService.updateCustomerIssue(99L, updateDTO);
		});
		verify(customerIssueRepository).findById(99L);
		verify(customerIssueRepository, never()).save(any(CustomerIssue.class));
	}
}
