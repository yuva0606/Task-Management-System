package com.yuva.app.security.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.yuva.app.entities.Role;
import com.yuva.app.security.entities.RefreshToken;
import com.yuva.app.security.entities.User;
import com.yuva.app.security.repository.UserRepo;
import com.yuva.app.utils.JwtResponse;
import com.yuva.app.utils.LoginRequest;
import com.yuva.app.utils.LoginResponse;
import com.yuva.app.utils.RegisterRequest;
import com.yuva.app.utils.RegisterResponse;

@Service
public class UserService {

	private final AuthenticationManager authManager;

	private final JwtService jwtService;

	private final UserRepo userRepo;

	private final RefreshTokenService refreshTokenService;

	private final PasswordEncoder bcrypt;

	public UserService(AuthenticationManager authManager, JwtService jwtService, UserRepo userRepo,
			RefreshTokenService refreshTokenService, PasswordEncoder bcrypt) {
		super();
		this.authManager = authManager;
		this.jwtService = jwtService;
		this.userRepo = userRepo;
		this.refreshTokenService = refreshTokenService;
		this.bcrypt = bcrypt;
	}

	public LoginResponse login(LoginRequest lreq) {
		Authentication authentication = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(lreq.username(), lreq.password()));
		String token = null;
		if (authentication.isAuthenticated()) {
			token = jwtService.generateToken(lreq.username());
		}

		String refreshToken = refreshTokenService.createRefreshToken(lreq.username());
		return new LoginResponse(lreq.username(), token, refreshToken);
	}

	public RegisterResponse registerUser(RegisterRequest regReq) {
		User newUser = new User(null, regReq.username(), regReq.email(), bcrypt.encode(regReq.password()), Role.USER,
				null);
		User savedUser = userRepo.save(newUser);

		return new RegisterResponse(savedUser.getUserId(), savedUser.getUsername(), savedUser.getEmail(),
				savedUser.getRole());
	}

	public JwtResponse generateNewJwt(String refreshToken) {
		RefreshToken savedRefreshToken = refreshTokenService.verifyRefreshToken(refreshToken);
		User user = userRepo.findByUsername(savedRefreshToken.getUser().getUsername()).get();
		String jwt = jwtService.generateToken(user.getUsername());

		return new JwtResponse(jwt, user.getUsername(), user.getEmail());
	}

	public Role getRoleByUsername(String username) {
		return userRepo.findByUsername(username).get().getRole();
	}

	public String changePassword(String password, String reEnterPassword, String email) {
		userRepo.updatePassword(bcrypt.encode(password), email);
		return "Your Password changed successffully";
	}

}
