package com.example.microservice.test.pre.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ErrorResponse {
	private LocalDateTime timestamp;
	private String message;
	private String detail;
	private String path;
}
