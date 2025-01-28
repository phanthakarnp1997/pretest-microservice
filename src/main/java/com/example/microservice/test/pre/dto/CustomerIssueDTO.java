package com.example.microservice.test.pre.dto;

import com.example.microservice.test.pre.constant.RequestStatus;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Customer issue request")
public class CustomerIssueDTO {
	@Schema(description = "Customer issue description")
	private String description;
	
	@Schema(description = "Customer issue forward to who fix issue")
	private String forwardTo;
	
	@Schema(description = "Customer issue status IN_PROGRESS,COMPLETED,CLOSED")
	private RequestStatus status;
}
