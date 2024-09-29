package com.yuva.app.exceptions;

public class RefreshTokenExpiredException extends RuntimeException {

	public RefreshTokenExpiredException(String message) {
		super(message);
	}
}
