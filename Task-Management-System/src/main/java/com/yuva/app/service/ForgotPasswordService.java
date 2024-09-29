package com.yuva.app.service;

import java.util.Date;
import java.util.Random;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.yuva.app.entities.ForgotPassword;
import com.yuva.app.exceptions.EmailNotFoundException;
import com.yuva.app.exceptions.OtpNotFoundException;
import com.yuva.app.repository.ForgotPasswordRepo;
import com.yuva.app.security.entities.User;
import com.yuva.app.security.repository.UserRepo;
import com.yuva.app.utils.MailBody;

@Service
public class ForgotPasswordService {

	private final UserRepo userRepo;

	private final EmailService emailService;

	private final ForgotPasswordRepo forgotPasswordRepo;

	private final PasswordEncoder passwordEncoder;

	public ForgotPasswordService(UserRepo userRepo, EmailService emailService, ForgotPasswordRepo forgotPasswordRepo,
			PasswordEncoder passwordEncoder) {
		super();
		this.userRepo = userRepo;
		this.emailService = emailService;
		this.forgotPasswordRepo = forgotPasswordRepo;
		this.passwordEncoder = passwordEncoder;
	}

	public String sendOtpMail(String email) {

		User user = userRepo.findByEmail(email).orElseThrow(() -> new EmailNotFoundException("Enter a valid Email!"));

		Random random = new Random();
		Integer otp = random.nextInt(100000, 999999);

		MailBody mailbody = new MailBody(email, "otp for Forgot Password", "This is your otp :" + otp);

		ForgotPassword fp = new ForgotPassword(null, otp, new Date(System.currentTimeMillis() + 50 * 1000), user);

		emailService.sendSimpleMessage(mailbody);

		forgotPasswordRepo.save(fp);

		return "otp mail send for verification";
	}

	public String verifyOtp(int otp, String email) {

		User user = userRepo.findByEmail(email)
				.orElseThrow(() -> new EmailNotFoundException("Enter the correct email!"));
		ForgotPassword fg = forgotPasswordRepo.findByOtpAndUser(otp, user)
				.orElseThrow(() -> new OtpNotFoundException("enter the correct otp!"));
		if (fg.getExpirationTime().before(new Date(System.currentTimeMillis()))) {
			forgotPasswordRepo.delete(fg);
			return "otp is expired, Try again!";
		}
		return "otp verification is success";
	}

	public String updatePassword(String password, String repassword, String email) {
		if (!password.equals(repassword)) {
			return "Enter the same Password";
		}
		userRepo.updatePassword(passwordEncoder.encode(password), email);
		return "Password updated";
	}

}
