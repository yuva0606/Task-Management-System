package com.yuva.app.utils;

public record UpdatePasswordRequest(String password, String reEnterPassword, String email) {

	public String password() {
		return password;
	}

	public String reEnterPassword() {
		return reEnterPassword;
	}

	public String email() {
		return email;
	}
	
	
	
}
