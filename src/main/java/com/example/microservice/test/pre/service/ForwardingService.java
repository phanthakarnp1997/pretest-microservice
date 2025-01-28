package com.example.microservice.test.pre.service;

import org.springframework.stereotype.Service;

import com.example.microservice.test.pre.api.BackendServiceClient;
import com.example.microservice.test.pre.api.FeignClientFactory;
import com.example.microservice.test.pre.dto.CustomerIssueDTO;
import com.example.microservice.test.pre.exception.ForwardRoutingNotFound;
import com.example.microservice.test.pre.model.RoutingRule;
import com.example.microservice.test.pre.repository.RoutingRuleRepository;

@Service
public class ForwardingService {

	private final RoutingRuleRepository routingRuleRepository;

	private final FeignClientFactory feignClientFactory;

	public ForwardingService(RoutingRuleRepository routingRuleRepository, FeignClientFactory feignClientFactory) {
		this.routingRuleRepository = routingRuleRepository;
		this.feignClientFactory = feignClientFactory;
	}

	public String forwardRequest(CustomerIssueDTO request) {
		RoutingRule routingRule = routingRuleRepository.findById(request.getForwardTo())
				.orElseThrow(() -> new ForwardRoutingNotFound("forward_to " + request.getForwardTo() + " not found."));

		if (routingRule.getServiceUrl() == null) {
			throw new IllegalArgumentException("No routing found for request type: " + request.getForwardTo());
		}

		BackendServiceClient client = feignClientFactory.createClient(routingRule.getServiceUrl());

		return client.forwardRequest(request);
	}
}
