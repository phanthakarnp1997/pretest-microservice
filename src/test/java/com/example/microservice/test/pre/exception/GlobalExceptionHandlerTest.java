package com.example.microservice.test.pre.exception;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.microservice.test.pre.dto.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
		request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/v1/customer/issue");
    }

    @Test
    void testHandleRuntimeException() {
        // Given
        RuntimeException exception = new RuntimeException("Internal server error");

        // When
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleException(exception, request);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal server error", response.getBody().getMessage());
        assertEquals("An unexpected error occurred", response.getBody().getDetail());
        assertEquals("/api/v1/customer/issue", response.getBody().getPath());
    }

    @Test
    void testHandleIssueNotFoundException() {
        // Given
        IssueNotFoundException exception = new IssueNotFoundException("Issue not found");

        // When
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleIssueNotFound(exception, request);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Issue not found", response.getBody().getMessage());
        assertEquals("Issue not found", response.getBody().getDetail());
        assertEquals("/api/v1/customer/issue", response.getBody().getPath());
    }

    @Test
    void testHandleForwardRoutingNotFoundException() {
        // Given
        ForwardRoutingNotFound exception = new ForwardRoutingNotFound("Forward routing not found");

        // When
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleForwardRoutingNotFoundException(exception, request);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Forward routing not found", response.getBody().getMessage());
        assertEquals("Forward routing not found, plese check forwardTo is correct", response.getBody().getDetail());
        assertEquals("/api/v1/customer/issue", response.getBody().getPath());
    }
}
