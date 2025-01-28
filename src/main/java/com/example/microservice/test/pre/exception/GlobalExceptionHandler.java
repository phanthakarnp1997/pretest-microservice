package com.example.microservice.test.pre.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.microservice.test.pre.dto.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = { RuntimeException.class })
	public ResponseEntity<ErrorResponse> handleException(Exception exception, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ErrorResponse.builder().timestamp(LocalDateTime.now()).message(exception.getMessage())
						.detail("An unexpected error occurred").path(request.getRequestURI()).build());
	}

}
