package com.example.microservice.test.pre.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.microservice.test.pre.api.BackendServiceClient;
import com.example.microservice.test.pre.api.FeignClientFactory;
import com.example.microservice.test.pre.constant.RequestStatus;
import com.example.microservice.test.pre.dto.CustomerIssueDTO;
import com.example.microservice.test.pre.exception.ForwardRoutingNotFound;
import com.example.microservice.test.pre.model.RoutingRule;
import com.example.microservice.test.pre.repository.RoutingRuleRepository;

@ExtendWith(MockitoExtension.class)
public class ForwardingServiceTest {

	@Mock
	private RoutingRuleRepository routingRuleRepository;

	@Mock
	private FeignClientFactory feignClientFactory;

	@InjectMocks
	private ForwardingService forwardingService;

	private CustomerIssueDTO customerIssueDTO;
	private RoutingRule routingRule;
	private BackendServiceClient backendServiceClient;

	@BeforeEach
	void setUp() {
		customerIssueDTO = new CustomerIssueDTO();
		customerIssueDTO.setDescription("Issue with payment");
		customerIssueDTO.setForwardTo("credit_card");
		customerIssueDTO.setStatus(RequestStatus.IN_PROGRESS);

		routingRule = new RoutingRule();
		routingRule.setServiceUrl("http://credit-card-service/api/v1/issue");

		backendServiceClient = mock(BackendServiceClient.class);
	}

	@Test
	void testForwardRequest_Success() {
		// Given
		when(routingRuleRepository.findById("credit_card")).thenReturn(java.util.Optional.of(routingRule));
		when(feignClientFactory.createClient("http://credit-card-service/api/v1/issue"))
				.thenReturn(backendServiceClient);
		when(backendServiceClient.forwardRequest(customerIssueDTO)).thenReturn("Success");

		// When
		String result = forwardingService.forwardRequest(customerIssueDTO);

		// Then
		assertEquals("Success", result);
		verify(routingRuleRepository, times(1)).findById("credit_card");
		verify(feignClientFactory, times(1)).createClient("http://credit-card-service/api/v1/issue");
		verify(backendServiceClient, times(1)).forwardRequest(customerIssueDTO);
	}

	@Test
	void testForwardRequest_RoutingRuleNotFound() {
		// Given
		when(routingRuleRepository.findById("credit_card")).thenReturn(java.util.Optional.empty());

		// When
		ForwardRoutingNotFound exception = assertThrows(ForwardRoutingNotFound.class, () -> {
			forwardingService.forwardRequest(customerIssueDTO);
		});

		// Then
		assertEquals("forward_to credit_card not found.", exception.getMessage());
		verify(routingRuleRepository, times(1)).findById("credit_card");
		verify(feignClientFactory, never()).createClient(Mockito.anyString());
		verify(backendServiceClient, never()).forwardRequest(Mockito.any());
	}

	@Test
	void testForwardRequest_ServiceUrlNull() {
		// Given
		routingRule.setServiceUrl(null);
		when(routingRuleRepository.findById("credit_card")).thenReturn(java.util.Optional.of(routingRule));

		// When
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			forwardingService.forwardRequest(customerIssueDTO);
		});

		// Then
		assertEquals("No routing found for request type: credit_card", exception.getMessage());
		verify(routingRuleRepository, times(1)).findById("credit_card");
		verify(feignClientFactory, never()).createClient(Mockito.anyString());
		verify(backendServiceClient, never()).forwardRequest(Mockito.any());
	}
}