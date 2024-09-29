package com.yuva.app.utils;

public record LoginRequest(String username, String password) {

	public String username() {
		return username;
	}

	public String password() {
		return password;
	}

	
}
