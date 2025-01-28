package com.example.microservice.test.pre.exception;

public class ForwardRoutingNotFound extends RuntimeException {

	public ForwardRoutingNotFound(String message) {
		super(message);
	}

	public ForwardRoutingNotFound(String message, Throwable cause) {
		super(message, cause);
	}
}
