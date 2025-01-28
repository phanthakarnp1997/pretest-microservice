package com.example.microservice.test.pre.api;

import com.example.microservice.test.pre.dto.CustomerIssueDTO;

public class BackendServiceClientFallback implements BackendServiceClient {

	@Override
	public String forwardRequest(CustomerIssueDTO request) {
		return "Service " + request.getForwardTo() + " is currently unavailable, please try again later.";
	}

}
