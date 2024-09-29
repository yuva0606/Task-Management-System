package com.yuva.app.utils;

public record OtpRequest(Integer otp, String email) {

	public Integer otp() {
		return otp;
	}

	public String email() {
		return email;
	}
	
	

}
