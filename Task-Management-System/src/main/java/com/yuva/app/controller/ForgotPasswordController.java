package com.yuva.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yuva.app.service.ForgotPasswordService;
import com.yuva.app.utils.OtpRequest;
import com.yuva.app.utils.UpdatePasswordRequest;

@RestController
@RequestMapping("/forgotPassword")
public class ForgotPasswordController {

	private final ForgotPasswordService forgotPasswordService;

	public ForgotPasswordController(ForgotPasswordService forgotPasswordService) {
		super();
		this.forgotPasswordService = forgotPasswordService;
	}

	// to send the forgot-password-otp to the user
	@PostMapping("/email/{email}")
	public ResponseEntity<String> sendVerfiEmail(@PathVariable String email) {
		return ResponseEntity.ok(forgotPasswordService.sendOtpMail(email));
	}

	// to verify the otp entered by the user
	@PostMapping("/otp")
	public ResponseEntity<String> verifyOtp(@RequestBody OtpRequest otpRequest) {
		return ResponseEntity.ok(forgotPasswordService.verifyOtp(otpRequest.otp(), otpRequest.email()));
	}

	// to update the user's password after otp verification
	@PostMapping("/updatePassword")
	public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest) {
		return ResponseEntity.ok(forgotPasswordService.updatePassword(updatePasswordRequest.password(),
				updatePasswordRequest.reEnterPassword(), updatePasswordRequest.email()));
	}

}
