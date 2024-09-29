package com.yuva.app.exceptions;

public class RefreshTokenNotFoundException extends RuntimeException {

	public RefreshTokenNotFoundException(String message) {
		super(message);
	}
}
