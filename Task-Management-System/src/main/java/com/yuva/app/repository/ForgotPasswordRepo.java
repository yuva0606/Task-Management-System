package com.yuva.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yuva.app.entities.ForgotPassword;
import com.yuva.app.security.entities.User;

public interface ForgotPasswordRepo extends JpaRepository<ForgotPassword, Integer> {

	Optional<ForgotPassword> findByOtpAndUser(int otp, User user);

}
