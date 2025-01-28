package com.example.microservice.test.pre.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ApiResponse<T> {
	private boolean success;
	private String message;
	private T data;

	public static <T> ApiResponse<T> success(T data) {
		return ApiResponse.<T>builder().message("request successfully.").success(true).data(data).build();
	}

	public static <T> ApiResponse<T> error(String message) {
		return ApiResponse.<T>builder().message(message).success(false).build();
	}
}
