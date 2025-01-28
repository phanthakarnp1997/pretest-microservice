package com.example.microservice.test.pre.dto;

import com.example.microservice.test.pre.constant.RequestStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CustomerIssueDTO {
	private String description;
	private String forwardTo;
	private RequestStatus status;
}
