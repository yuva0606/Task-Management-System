package com.yuva.app.exceptions;

public class EmailNotFoundException extends RuntimeException{
	
	public EmailNotFoundException(String message) {
		super(message);
	}

}
