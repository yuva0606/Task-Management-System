package com.yuva.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.yuva.app.security.repository.UserRepo;

@Configuration
@EnableWebSecurity
public class JavaConfiguration {

	private final UserRepo userRepo;

	public JavaConfiguration(UserRepo userRepo) {
		super();
		this.userRepo = userRepo;
	}

	@Bean
	UserDetailsService getUserDetailsService() {
		return new UserDetailsService() {

			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				return userRepo.findByUsername(username)
						.orElseThrow(() -> new UsernameNotFoundException("username " + username + " is not exist"));
			}
		};
	}

	@Bean
	AuthenticationProvider authProvider() {
		DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
		dao.setUserDetailsService(getUserDetailsService());
		dao.setPasswordEncoder(getPasswordEncoder());
		System.out.println("Created DAO auth providwe!!!");
		return dao;
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration confi) throws Exception {
		return confi.getAuthenticationManager();
	}

	@Bean
	PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder(12);
	}

}
