package com.yuva.app.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.yuva.app.security.entities.User;

import jakarta.transaction.Transactional;

public interface UserRepo extends JpaRepository<User, Integer>{

	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);

	@Transactional
	@Modifying
	@Query("update User u set u.password = ?1 where u.email = ?2")
	void updatePassword(String password, String email);

}
