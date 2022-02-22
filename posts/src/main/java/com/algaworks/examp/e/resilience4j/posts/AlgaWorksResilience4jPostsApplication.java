package com.algaworks.examp.e.resilience4j.posts;

import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigCustomizer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AlgaWorksResilience4jPostsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlgaWorksResilience4jPostsApplication.class, args);
	}

}
