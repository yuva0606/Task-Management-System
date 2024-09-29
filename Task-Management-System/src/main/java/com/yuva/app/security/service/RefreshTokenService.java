package com.yuva.app.security.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yuva.app.exceptions.RefreshTokenExpiredException;
import com.yuva.app.security.entities.RefreshToken;
import com.yuva.app.security.entities.User;
import com.yuva.app.security.repository.RefreshTokenRepo;
import com.yuva.app.security.repository.UserRepo;

@Service
public class RefreshTokenService {

	private final RefreshTokenRepo refreshTokenRepo;

	private final UserRepo userRepo;

	public RefreshTokenService(RefreshTokenRepo refreshTokenRepo, UserRepo userRepo) {
		super();
		this.refreshTokenRepo = refreshTokenRepo;
		this.userRepo = userRepo;
	}

	// creating new refresh token
	public String createRefreshToken(String username) {
		// getting the user
		User user = userRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("username " + username + " is not exist"));
		// getting the user's refresh token
		RefreshToken token = user.getRefreshToken();
		// if user not yet have a refresh then creating it
		if (token == null) {
			token = new RefreshToken(null, UUID.randomUUID().toString(), Instant.now().plusMillis(5 * 60 * 60 * 1000),
					user);

			// saving the created refresh token
			refreshTokenRepo.save(token);
		}

		return token.getRefreshtoken();
	}

	public RefreshToken verifyRefreshToken(String token) {

		RefreshToken refreshToken = refreshTokenRepo.findByRefreshtoken(token).get();
		if (refreshToken.getExpirationTime().compareTo(Instant.now()) < 0) {
			refreshTokenRepo.delete(refreshToken);
			throw new RefreshTokenExpiredException("Kindly Login again");
		}

		return refreshToken;
	}

}
