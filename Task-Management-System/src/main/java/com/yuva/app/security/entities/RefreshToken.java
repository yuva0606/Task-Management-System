package com.yuva.app.security.entities;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class RefreshToken {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer tokenId;
	
	@Column(nullable = false,length = 500)
	@NotBlank(message = "enter refreshToken")
	private String refreshtoken;
	
	@Column(nullable = false)
	private Instant expirationTime;
	
	@OneToOne
	private User user;

	public Integer getTokenId() {
		return tokenId;
	}

	public void setTokenId(Integer tokenId) {
		this.tokenId = tokenId;
	}

	public String getRefreshtoken() {
		return refreshtoken;
	}

	public void setRefreshToken(String refreshtoken) {
		this.refreshtoken = refreshtoken;
	}

	public Instant getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(Instant expirationTime) {
		this.expirationTime = expirationTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public RefreshToken(Integer tokenId, @NotBlank(message = "enter refreshtoken") String refreshtoken,
			Instant expirationTime, User user) {
		super();
		this.tokenId = tokenId;
		this.refreshtoken = refreshtoken;
		this.expirationTime = expirationTime;
		this.user = user;
	}
	
	public RefreshToken() {
		// TODO Auto-generated constructor stub
	}
	
	

}
