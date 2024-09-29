package com.yuva.app.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yuva.app.security.service.UserService;
import com.yuva.app.utils.JwtResponse;
import com.yuva.app.utils.LoginRequest;
import com.yuva.app.utils.LoginResponse;
import com.yuva.app.utils.RegisterRequest;
import com.yuva.app.utils.RegisterResponse;
import com.yuva.app.utils.UpdatePasswordRequest;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> loginHandler(@RequestBody LoginRequest loginRequest){
		System.out.println("In Login controller!");
		return ResponseEntity.ok(userService.login(loginRequest));
		
	}
	
	@PostMapping("/register")
	public ResponseEntity<RegisterResponse> registerHandler(@RequestBody RegisterRequest registerRequest){
		return ResponseEntity.ok(userService.registerUser(registerRequest));
	}
	
	//to get new JWT from refresh token
	@PostMapping("/jwt")
	public ResponseEntity<JwtResponse> refreshTokens(@RequestParam String refreshToken){
		System.out.println("Yeah yay");
		return ResponseEntity.ok(userService.generateNewJwt(refreshToken));
	}
	
	//to change user's password
	@PostMapping("/changePassword")
	public ResponseEntity<String> changePassword(@RequestBody UpdatePasswordRequest passwordRequest){
		return ResponseEntity.ok(userService.changePassword(passwordRequest.password(),
				                                            passwordRequest.reEnterPassword(),
				                                            passwordRequest.email()));
	}
	
	@GetMapping("/test")
	public String test() {
		return "Successfully!";
	}

}
