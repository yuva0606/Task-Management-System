package com.yuva.app.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yuva.app.security.entities.RefreshToken;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Integer> {

	Optional<RefreshToken> findByRefreshtoken(String token);

}
