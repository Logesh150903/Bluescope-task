package com.example.boot.exception;

public class ApiProcessingException extends Exception {
	public ApiProcessingException(String message) {
		super(message);
	}

	public ApiProcessingException(String message, Throwable cause) {
		super(message, cause);
	}
}