package com.example.microservice.test.pre.exception;

public class IssueNotFoundException extends RuntimeException {

	public IssueNotFoundException(String message) {
		super(message);
	}

	public IssueNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
