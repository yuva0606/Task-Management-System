package com.yuva.app.entities;

import java.util.Date;

import com.yuva.app.security.entities.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class ForgotPassword {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer fId;

	@Column(nullable = false)
	private Integer otp;

	@Column(nullable = false)
	private Date ExpirationTime;

	@OneToOne
	private User user;

	public Integer getfId() {
		return fId;
	}

	public void setfId(Integer fId) {
		this.fId = fId;
	}

	public Integer getOtp() {
		return otp;
	}

	public void setOtp(Integer otp) {
		this.otp = otp;
	}

	public Date getExpirationTime() {
		return ExpirationTime;
	}

	public void setExpirationTime(Date expirationTime) {
		ExpirationTime = expirationTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ForgotPassword(Integer fId, Integer otp, Date expirationTime, User user) {
		super();
		this.fId = fId;
		this.otp = otp;
		ExpirationTime = expirationTime;
		this.user = user;
	}

	public ForgotPassword() {
		// TODO Auto-generated constructor stub
	}

}
