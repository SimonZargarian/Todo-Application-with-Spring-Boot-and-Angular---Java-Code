package com.kokabmedia.jwt.resource;

/*
 * This class handles Authentication Exceptions for the application.
 */
public class AuthenticationException extends RuntimeException {
	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}
}
