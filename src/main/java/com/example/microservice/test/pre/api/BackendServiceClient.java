package com.example.microservice.test.pre.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.microservice.test.pre.dto.CustomerIssueDTO;

@FeignClient(name = "backend-service", fallback = BackendServiceClientFallback.class)
public interface BackendServiceClient {
	@PostMapping()
	String forwardRequest(@RequestBody CustomerIssueDTO request);
}
