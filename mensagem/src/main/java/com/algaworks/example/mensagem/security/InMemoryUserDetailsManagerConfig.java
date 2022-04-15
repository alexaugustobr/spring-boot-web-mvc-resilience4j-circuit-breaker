package com.algaworks.example.mensagem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class InMemoryUserDetailsManagerConfig {

	@Bean
	public InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder passwordEncoder){
		final var inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
		final var usuario = User.withUsername("actuator")
				.password(passwordEncoder.encode("123"))
				.roles("ACTUATOR")
				.build();
		inMemoryUserDetailsManager.createUser(usuario);
		return inMemoryUserDetailsManager;
	}
	
}
